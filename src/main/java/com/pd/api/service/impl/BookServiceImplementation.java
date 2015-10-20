package com.pd.api.service.impl;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.BookFavorited;
import com.pd.api.entity.BookRating;
import com.pd.api.entity.BookReading;
import com.pd.api.entity.BookSuggestions;
import com.pd.api.entity.BookWishlisted;
import com.pd.api.entity.Language;
import com.pd.api.entity.NewBookRequest;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.entity.Work;
import com.pd.api.entity.WorkRating;
import com.pd.api.entity.aux.BookInfo;
import com.pd.api.entity.aux.BookRequestWrapper;
import com.pd.api.entity.aux.PosdtaWrapper;
import com.pd.api.entity.aux.UserToBook;
import com.pd.api.exception.InvalidParameterException;
import com.pd.api.exception.InvalidStateException;
import com.pd.api.util.LuceneIndexer;

public class BookServiceImplementation {

    public static List<Book> getBooks(int start, int limit) {
        return DAO.getAll(Book.class, start, limit);
    }
    
    /**
     * 
     * @param start
     * @param limit
     * @return A list of the most read books
     */
    public static List<Book> getMostRead(int start, int limit) {
        //book_id, count(*) as mostRead FROM `posdta` group by book_id order by mostRead desc
        return DAO.getAllBooksFromQuery("SELECT distinct p.book from Posdta p group by p.book.id order by count(*) desc", start, limit);
    }
    
    public static Book getBookById(Long id) {
        return DAO.get(Book.class, id);
    }
    
    public static BookInfo getBookInfo(Long id) {
        BookInfo info = DAO.getBookInfo(id);
        return info;
    }
    
    public static WorkRating getBookDetailedRating(Long bookId) {
        Book book = DAO.get(Book.class, bookId);
        WorkRating wr = DAO.getUnique(WorkRating.class, "where work = ?", book.getWork());
        if(wr == null) {
            wr = new WorkRating(book.getWork());
            DAO.put(wr);
        }
        return wr;
        
    }
    
    /**
     * 
     * @param bookId
     * @param start
     * @param limit
     * @return List of users that marked this book as a favorite
     */
    public static List<User> getBookFavorites(Long bookId, int start, int limit) {
        //Book book = DAO.get(Book.class, bookId);
        return DAO.getAllUsersFromQuery("select distinct bf.user from BookFavorited bf where bf.book.id = ?", start, limit, bookId);
    }
    
    /**
     * 
     * @param bookId
     * @param start
     * @param limit
     * @return List of users that have this book on their wish list
     */
    public static List<User> getBookWishlists(Long bookId, int start, int limit) {
        return DAO.getAllUsersFromQuery("select distinct bw.user from BookWishlisted bw where bw.book.id = ?", start, limit, bookId);
    }
    
    /**
     * 
     * @param bookId
     * @param start
     * @param limit
     * @return List of users that are currently reading this book
     */
    public static List<User> getBookReading(Long bookId, int start, int limit) {
        return DAO.getAllUsersFromQuery("select distinct br.user from BookReading br where br.book.id = ?", start, limit, bookId);
    }
    
    /**
     * 
     * @param bookId
     * @param start
     * @param limit
     * @return List of Posdtas for a particular book
     */
    public static List<Posdta> getBookPosdtas(Long bookId, int start, int limit) {
        return DAO.getAll(Posdta.class, "where book.id = ?", "", start, limit, bookId);
    }
    
    //get reading by book
    
    /**
     * Action: mark a book as being read by the user
     * if the book was in the user wish list remove it (since he already has it)
     * @param username
     * @param bookId
     */
    public static void startReadingBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        //Check if he was reading already
        BookReading reading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading != null) return;
        reading = new BookReading(user, book);
        DAO.put(reading);
        //Update book reading info
        BookRating rating = book.getRating();
        rating.addReading();DAO.put(rating);
        //Check if it was wish listed and remove
        unwishlistBook(user, book);
    }
    
    /**
     * Action: stop reading a book
     * You can only stop reading a book after you left a Posdta
     * @param username
     * @param bookId
     */
    public static void stopReadingBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookReading reading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading == null) throw new InvalidStateException("You have to start reading this book first");
        Posdta posdta = DAO.getUnique(Posdta.class, "where user = ? and book = ? ", user, book);
        if(posdta == null) throw new InvalidStateException("You have to write a Posdta of this book");
        //TODO: implement the event now delete the reading
        DAO.remove(reading.getClass(), reading.getId());
        BookRating rating = book.getRating();
        rating.removeReading();
        DAO.put(rating);
    }
    
    /**
     * Action save a Posdta
     * @param username
     * @param bookId
     * @param posdtaWrapper
     * @return the created posdta
     */
    public static Posdta savePosdta(String username, Long bookId, PosdtaWrapper posdtaWrapper) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookReading reading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading == null) throw new InvalidStateException("You have to start reading this book first"); //Not reading
        //Check if there was another posdta
        Posdta posdta = DAO.getUnique(Posdta.class, "where user = ? and book = ? ", user, book);
        if(posdta != null) throw new InvalidStateException("There is already a Posdta for this book"); //Duplicate posdta 
        posdta = posdtaWrapper.getPosdta(reading);
        DAO.put(posdta);
        //update rating
        WorkRating wr = DAO.getUnique(WorkRating.class, "where work = ?", book.getWork());
        if(wr == null) wr = new WorkRating(book.getWork());
        wr.updateCounter(posdtaWrapper.getRating());
        DAO.put(wr);
        Work work = book.getWork();
        work.updateRating(wr);
        DAO.put(work);
        DAO.remove(reading.getClass(), reading.getId());
        BookRating rating = book.getRating();
        rating.addRating(posdta.getRating());
        rating.removeReading();
        DAO.put(rating);
        return posdta;
    }
    
    /**
     * Action: mark a book as a favorite for the user
     * @param username
     * @param bookId
     */
    public static void favoriteBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookFavorited favorited = DAO.getUnique(BookFavorited.class, "where user = ? and book = ? ", user, book);
        if(favorited != null) return;
        favorited = new BookFavorited(user, book);
        DAO.put(favorited);
        BookRating rating = book.getRating();
        rating.addFavorited();
        DAO.put(rating);
    }
    
    /**
     * Action: remove book from user favorites
     * @param username
     * @param bookId
     */
    public static void unfavoriteBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookFavorited favorited = DAO.getUnique(BookFavorited.class, " where user = ? and book = ?", user, book);
        if(favorited != null) {
            DAO.delete(favorited);
            BookRating rating = book.getRating();
            rating.removeFavorited();
            DAO.put(rating);
        }
    }
    
    /**
     * Action: insert a book in the users wish list
     * @param username
     * @param bookId
     */
    public static void wishlistBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookWishlisted wishlisted = DAO.getUnique(BookWishlisted.class, "where user = ? and book = ? ", user, book);
        if(wishlisted != null)return;
        wishlisted = new BookWishlisted(user, book);
        DAO.put(wishlisted);
        BookRating rating = book.getRating();
        rating.addWishlisted();
    }
    
    /**
     * Action: remove a book from the user's wishlist
     * @param username
     * @param bookId
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
            BookRating rating = book.getRating();
            rating.removeWishlisted();
            DAO.put(rating);
        }
    }
    
    /**
     * 
     * @param username
     * @param bookId
     * @return
     */
    public static UserToBook getUserToBookInteraction(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        UserToBook userToBook = new UserToBook(user, book);
        return userToBook;
    }
    
    /**
     * Request for a new book to be inserted
     * @param username
     * @param newRequestWrapper
     * @return the book created
     */
    public static Book requestNewBook(String username, BookRequestWrapper request) {
        User user = DAO.getUserByUsername(username);
        Work work = null;
        Book book = null;
        Author author = null;
        Language lang = DAO.getLanguageByCode(request.getLanguageString());
        if(lang == null) throw new InvalidParameterException("Invalid language");
        if(request.hasNewAuthor()) {
            //Save the author and create the work
            author = new Author(request.getAuthorString());
            DAO.put(author);
            work = new Work(author, request.getTitle(), lang);
            DAO.put(work);
        } else {
            author = DAO.get(Author.class, request.getauthorId());
            if(author == null) throw new InvalidParameterException("The author does not exist");
            //Check if the work exists
            if(request.hasNewWork()) {
                work = new Work(author, request.getTitle(), lang);
                DAO.put(work);
            } else {
                work = DAO.get(Work.class, request.getWorkId());
                if(work == null) throw new InvalidParameterException("The work does not exist");
            }
        }
        book = new Book(work, request.getTitle(), lang);
        book = DAO.put(book);
        //Create bookRequest
        NewBookRequest requestLog = new NewBookRequest(user, request.getTitle(), request.getAuthorString(), request.getLanguageString());
        DAO.put(requestLog);
        //Index
        //TODO: mark for indexing
        /*LuceneIndexer indexer = LuceneIndexer.getInstance();
        indexer.indexBook(book);
        if(request.hasNewAuthor())indexer.indexAuthor(author);*/
        return book;
    }
    
    public static List<NewBookRequest> getBookRequests(int first, int limit) {
        return DAO.getAll(NewBookRequest.class, first, limit);
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
