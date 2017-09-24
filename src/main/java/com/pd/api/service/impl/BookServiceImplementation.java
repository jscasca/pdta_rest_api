package com.pd.api.service.impl;

import java.math.BigInteger;
import java.util.*;

import com.pd.api.entity.*;
import com.pd.api.entity.aux.*;
import com.pd.api.exception.DuplicateResourceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pd.api.db.DAO;
import com.pd.api.exception.InvalidParameterException;
import com.pd.api.exception.InvalidStateException;

import static com.pd.api.entity.Event.*;
import static com.pd.api.entity.Event.EventType.NULL;
import static com.pd.api.entity.Event.EventType.POSDTA;
import static com.pd.api.entity.Event.EventType.RATED;

public class BookServiceImplementation {

    private static final Logger LOG = LoggerFactory.getLogger(BookServiceImplementation.class);
    
    public static List<Book> getBooks(int start, int limit) {
        return DAO.getAll(Book.class, start, limit);
    }
    
    /**
     * 
     * @param start the first element to return
     * @param limit the max number of elements
     * @return A list of the most read books
     */
    public static List<Book> getMostRead(int start, int limit) {
        //book_id, count(*) as mostRead FROM `posdta` group by book_id order by mostRead desc
        return DAO.getAllBooksFromQuery("SELECT distinct p.book from Posdta p group by p.book.id order by count(*) desc", start, limit);
    }
    
    public static List<Book> getTrendingBooks(int start, int limit) {
        //TODO: fix this method
        return DAO.getAllBooksFromQuery("SELECT distinct p.book from Posdta p group by p.book.id order by count(*) desc", start, limit);
    }

    //TODO: rework this method
    public static List<Book> getBooksWithTheSameAuthor(Long id, int start, int limit) {
        return new ArrayList<Book>();
        //Book b = DAO.get(Book.class, id);
        //return DAO.getAllBooksFromQuery("SELECT distinct b from Book b where b.work.author = ? and b.work <> ?", start, limit, b.getAuthors(), b.getWork());
    }
    
    public static Book getBookById(Long id) {
        return DAO.get(Book.class, id);
    }
    
    public static BookInfo getBookInfo(Long id) {
        return DAO.getBookInfo(id);
    }
    
    /**
     * 
     * @param bookId the book id
     * @param start the row to start from
     * @param limit the limit of results
     * @return List of users that marked this book as a favorite
     */
    public static List<User> getBookFavorites(Long bookId, int start, int limit) {
        //Book book = DAO.get(Book.class, bookId);
        return DAO.getAllUsersFromQuery("select distinct bf.user from BookFavorited bf where bf.book.id = ?", start, limit, bookId);
    }
    
    /**
     * 
     * @param bookId the book id
     * @param start the row to start returning results from
     * @param limit the max number of elements to return
     * @return List of users that have this book on their wish list
     */
    public static List<User> getBookWishlists(Long bookId, int start, int limit) {
        return DAO.getAllUsersFromQuery("select distinct bw.user from BookWishlisted bw where bw.book.id = ?", start, limit, bookId);
    }
    
    /**
     *
     * @param bookId the book id
     * @param start the row to start returning results from
     * @param limit the max number of elements to return
     * @return List of users that are currently reading this book
     */
    public static List<User> getBookReading(Long bookId, int start, int limit) {
        return DAO.getAllUsersFromQuery("select distinct br.user from BookReading br where br.book.id = ?", start, limit, bookId);
    }
    
    /**
     *
     * @param bookId the book id
     * @param start the row to start returning results from
     * @param limit the max number of elements to return
     * @return List of Posdtas for a particular book
     */
    public static List<Posdta> getBookPosdtas(Long bookId, int start, int limit) {
        return DAO.getAll(Posdta.class, "where book.id = ? and posdta is not null", "", start, limit, bookId);
    }
    
    public static List<Posdta> getBookPosdtasWithUserVotes(String username, Long bookId, int start, int limit) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        List<Posdta> posdtas = DAO.getAll(Posdta.class, "where book.id = ? and posdta is not null", "", start, limit, bookId);
        for(int i = 0; i < posdtas.size(); i++) {
            Posdta posdta = posdtas.get(i);
            UserVote vote = DAO.getUnique(UserVote.class, "where user = ? and posdta = ?", user, posdta);
            if(vote != null) posdtas.set(i, new PosdtaWithVote(vote));
        }
        return posdtas;
    }
    
    //get reading by book
    
    /**
     * Action: mark a book as being read by the user
     * if the book was in the user wish list remove it (since he already has it)
     * @param username the username of the user executing this action
     * @param bookId the target book id
     */
    public static void startReadingBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        LOG.debug(user.getUserName() + " to start reading " + book.getTitle());
        //Check if he was reading already
        BookReading reading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        
        if(reading != null) {
            LOG.debug("user is already reading this book");
            return;
        }
        reading = new BookReading(user, book);
        reading = DAO.put(reading);
        LOG.debug("Reading is saved");
        //Update book reading info
        book = DAO.put(book);
        LOG.debug("Book metrics are updated");
        //Check if it was wish listed and remove
        unwishlistBook(user, book);
        LOG.debug("Book was unwishlisted");
        DAO.saveEventWithBook(new EventWithBook(user, EventType.STARTED_READING, book));
        LOG.debug("Book reading event was saved");
    }
    
    /**
     * Action: stop reading a book
     * You can only stop reading a book after you left a Posdta
     * @param username the username of the user executing this action
     * @param bookId the target book id
     */
    public static void stopReadingBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookReading reading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading == null) throw new InvalidStateException("You have to start reading this book first");
        /*
        TODO: check wether users can stop reading without leaving a posdta
        Posdta posdta = DAO.getUnique(Posdta.class, "where user = ? and book = ? ", user, book);
        if(posdta == null) throw new InvalidStateException("You have to write a Posdta of this book");
        */
        //TODO: implement the event now delete the reading
        DAO.remove(reading.getClass(), reading.getId());
        DAO.put(book);
    }
    
    /**
     * Action save a Posdta
     * @param username the username of the user executing this action
     * @param bookId the target book id
     * @param posdtaWrapper the elements of the posdta (prologe)
     * @return the created posdta
     */
    //TODO: REDEFINE
    public static Posdta savePosdta(String username, Long bookId, PosdtaWrapper posdtaWrapper) {
        if(posdtaWrapper == null) { throw new NullPointerException(""); }
        Book book = DAO.get(Book.class, bookId);
        User user = DAO.getUserByUsername(username);
        Posdta posdta = DAO.getUnique(Posdta.class, "where user = ? and book = ? ", user, book); //Check if there was a Posdta already
        BookReading reading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        EventType type = NULL;
        //Complete
        if(posdtaWrapper.isRatingAndPosdta()) {
            if(posdta != null) throw new DuplicateResourceException("A posdta already exists for this book");
            if(reading != null) posdta = new Posdta(reading, posdtaWrapper.getPosdta(), posdtaWrapper.getRating());
            else posdta = new Posdta(user, book, posdtaWrapper.getPosdta(), posdtaWrapper.getRating());
            type = POSDTA;
        } else if(posdtaWrapper.isPosdtaOnly()) {
            if(posdta == null) throw new InvalidStateException("This posdta does not have a rating");
            if(posdta.getPosdta() != null) throw new DuplicateResourceException("This posdta already exists");
            posdta.setPosdta(posdtaWrapper.getPosdta());
            type = POSDTA;
        } else if(posdtaWrapper.isRatingOnly()) {
            if(posdta != null && posdta.getRating() > 0) throw new DuplicateResourceException("A rating already exist for this book");
            if(reading != null) posdta = new Posdta(reading, posdtaWrapper.getRating());
            else posdta = new Posdta(user, book, null, posdtaWrapper.getRating());
            type = RATED;
        }
        posdta = DAO.put(posdta);
        switch(type) {
            case POSDTA: DAO.put(new EventWithPosdta(user, POSDTA, posdta)); break;
            case RATED: DAO.put(new EventWithPosdta(user, RATED, posdta)); break;
            default: break;
        }
        if(reading != null) {
            DAO.remove(reading.getClass(), reading.getId());
        }
        BookWishlisted wishlisted = DAO.getUnique(BookWishlisted.class, "where user = ? and book = ?", user, book);
        if(wishlisted != null) {
            DAO.remove(wishlisted.getClass(), wishlisted.getId());
        }
        return posdta;
    }
    
    /**
     * Action: mark a book as a favorite for the user
     * @param username the username of the user executing this action
     * @param bookId the target book id
     */
    public static void favoriteBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookFavorited favorited = DAO.getUnique(BookFavorited.class, "where user = ? and book = ? ", user, book);
        if(favorited != null) return;
        favorited = new BookFavorited(user, book);
        favorited = DAO.put(favorited);
        book = DAO.put(book);
        DAO.saveEventWithBook(new EventWithBook(user, EventType.FAVORITED, book));
    }
    
    /**
     * Action: remove book from user favorites
     * @param username the username of the user executing this action
     * @param bookId the target book id
     */
    public static void unfavoriteBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookFavorited favorited = DAO.getUnique(BookFavorited.class, " where user = ? and book = ?", user, book);
        if(favorited != null) {
            DAO.delete(favorited);
            book = DAO.put(book);
        }
    }
    
    /**
     * Action: insert a book in the users wish list
     * @param username the username of the user executing this action
     * @param bookId the target book id
     */
    public static void wishlistBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookWishlisted wishlisted = DAO.getUnique(BookWishlisted.class, "where user = ? and book = ? ", user, book);
        if(wishlisted != null)return;
        wishlisted = new BookWishlisted(user, book);
        wishlisted = DAO.put(wishlisted);
        DAO.put(book);
        DAO.saveEventWithBook(new EventWithBook(user, EventType.WISHLISTED, book));
    }
    
    /**
     * Action: remove a book from the user's wishlist
     * @param username the username of the user executing this action
     * @param bookId the target book id
     */
    public static void unwishlistBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        unwishlistBook(user, book);
    }
    
    private static void unwishlistBook(User user, Book book) {
        BookWishlisted wishlisted = DAO.getUnique(BookWishlisted.class, " where user = ? and book = ?", user, book);
        if(wishlisted != null) {
            DAO.delete(wishlisted);
            book = DAO.put(book);
        }
    }
    
    /**
     *
     * @param username the username of the user executing this action
     * @param bookId the target book id
     * @return the interactions recorded for the username and the target book
     */
    public static UserToBook getUserToBookInteraction(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        UserToBook userToBook = new UserToBook(user, book);
        return userToBook;
    }

    public static Book requestCustomBook(String username, BookRequestWrapper request) {
        User user = DAO.getUserByUsername(username);
        Book book = requestNewBook(request);
        //do something with the new book
        BookRecordRequest brr = new BookRecordRequest(user, book);
        DAO.put(brr);
        //save an event with the user
        return book;
    }
    
    /**
     * Request for a new book to be inserted
     * @param request the wrapper with the elements of the requested book
     * @return the book created
     */
    public static Book requestNewBook(BookRequestWrapper request) {
        Language lang = DAO.getLanguageByCode(request.getLanguageString());
        if(lang == null) throw new InvalidParameterException("Invalid language");
        String icon = request.getIcon();
        String thumbnail = request.getThumbnail();
        List<Work> existing = DAO.getExistingWorks(request.getTitle(), lang);
        String[] authorNames = request.getAuthors();
        //Not convinced about this...
        for(Work w : existing) {
            for(String authorName : authorNames) {
                for(Author author : w.getAuthors()) {
                    if(author.getName().equals(authorName)) {
                        return DAO.getBookFromWork(w, lang);
                    }
                }
            }
        }
        //Loop authors
        Set<Author> authors = new HashSet<>();
        for(String authorName : authorNames) {
            //Look for each author or add them
            Author author = getAuthorFromString(authorName);
            authors.add(author);
        }
        Work work = new Work(authors, request.getTitle(), icon, thumbnail, lang);
        work = DAO.put(work);
        Book book = new Book(work, request.getTitle(), icon, thumbnail, lang);
        book = DAO.put(book);
        //Create bookRequest
        //BookRecordRequest request = new BookRecordRequest(book, user);
        //NewBookRequest requestLog = new NewBookRequest(user, request.getTitle(), request.getAuthorString(), request.getLanguageString());
        //DAO.put(requestLog);
        return book;
    }
    
    private static Author getAuthorFromString(String name) {
        Author closestMatch = DAO.getAuthorByName(name);
        if(closestMatch != null) return closestMatch;
        List<Author> possibleAuthors = SearchServiceImplementation.searchAuthors(name, 0, 3);
        int closestDistance = name.length();
        for(Author author : possibleAuthors) {
            if(author == null) continue;
            int  newDistance = StringUtils.getLevenshteinDistance(name.toLowerCase(), author.getName().toLowerCase());
            if(newDistance < closestDistance) {
                closestDistance = newDistance;
                closestMatch = author;
            }
        }
        if(closestMatch != null && closestDistance < 1) {
            return closestMatch;
        } else {
            Author newAuthor = new Author(name);
            return DAO.put(newAuthor);
        }
    }
    
    public static List<Book> getRandomSuggestions(String username, int limit) {
        //TODO: implement
        return null;
    }
    
    public static List<Book> browseSuggestions(String username, int first, int limit) {
        //TOOD: implement
        return null;
    }
    
    public static void updateSuggestions(String username) {
        User user = DAO.getUserByUsername(username);
        BookSuggestions bs = DAO.getUnique(BookSuggestions.class, "where user = ?", user);
        if(bs == null) {
            bs = new BookSuggestions(user);
            bs = DAO.put(bs);
        }
        HashMap<Long, Double> suggestionMap = new HashMap<Long, Double>();
        List<?> possibleBooks = DAO.createNativeQuery("select book_id, count(*) as popularity from posdta where user_id in (select user_id from posdta where book_id in (select book_id from posdta where user_id = '"+user.getId()+"') and user_id <> '"+user.getId()+"') and book_id not in (select book_id from posdta where user_id = '"+user.getId()+"') group by book_id order by popularity limit 100").getResultList();
        int suggestionSize = possibleBooks.size();
        int maxCount = 1;
        for(int i = 0; i < suggestionSize; i++) {
            Object[] o = (Object[]) possibleBooks.get(i);
            Integer count = ((BigInteger)o[1]).intValue();
            if(i == 0)maxCount = count;
            Long id = ((BigInteger)o[0]).longValue();
            Double value = (double) (count/maxCount);
            suggestionMap.put(id, value);
        }
        bs.updateMap(suggestionMap);
        DAO.put(bs);
    }
}
