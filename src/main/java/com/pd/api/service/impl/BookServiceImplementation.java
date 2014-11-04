package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.BookFavorited;
import com.pd.api.entity.BookReading;
import com.pd.api.entity.BookWishlisted;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.PosdtaWrapper;
import com.pd.api.exception.InvalidStateException;

public class BookServiceImplementation {

    public static List<Book> getBooks(int start, int limit) {
        return DAO.getAll(Book.class, start, limit);
    }
    
    public static Book getBookById(Long id) {
        return DAO.get(Book.class, id);
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
        return DAO.getAllFromQuery("select distinct bf.user from BookFavorited bf where bf.book.id = ?", start, limit, bookId);
    }
    
    /**
     * 
     * @param bookId
     * @param start
     * @param limit
     * @return List of users that have this book on their wish list
     */
    public static List<User> getBookWishlists(Long bookId, int start, int limit) {
        return DAO.getAllFromQuery("select distinct bw.user from BookWishlisted bw where bw.book.id = ?", start, limit, bookId);
    }
    
    /**
     * 
     * @param bookId
     * @param start
     * @param limit
     * @return List of users that are currently reading this book
     */
    public static List<User> getBookReading(Long bookId, int start, int limit) {
        return DAO.getAllFromQuery("select distinct br.user from BookReading br where br.book.id = ?", start, limit, bookId);
    }
    
    //get posdtas by book
    
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
        BookReading reading = DAO.getSingle(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading != null) return;
        reading = new BookReading(user, book);
        DAO.put(reading);
        //Check if it was wish listed and remove
        unwishlistBook(user, book);
    }
    
    //stop reading (only after you left a posdta)
    public static void stopReadingBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookReading reading = DAO.getSingle(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading == null) throw new InvalidStateException("","","");
        //TODO: implement the event now delete the reading
        DAO.delete(reading);
    }
    
    public static Posdta savePosdta(String username, Long bookId, PosdtaWrapper posdtaWrapper) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookReading reading = DAO.getSingle(BookReading.class, "where user = ? and book = ?", user, book);
        if(reading == null) throw new InvalidStateException("","",""); //Not reading
        //Check if there was another posdta
        Posdta posdta = DAO.getSingle(Posdta.class, "where user = ? and book = ? ", user, book);
        if(posdta != null) throw new InvalidStateException("","",""); //Duplicate posdta 
        posdta = posdtaWrapper.getPosdta(reading);
        
        return null;
    }
    
    /**
     * Action: mark a book as a favorite for the user
     * @param username
     * @param bookId
     */
    public static void favoriteBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookFavorited favorited = DAO.getSingle(BookFavorited.class, "where user = ? and book = ? ", user, book);
        if(favorited != null) return;
        favorited = new BookFavorited(user, book);
        DAO.put(favorited);
    }
    
    /**
     * Action: remove book from user favorites
     * @param username
     * @param bookId
     */
    public static void unfavoriteBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        BookFavorited favorited = DAO.getSingle(BookFavorited.class, " where user = ? and book = ?", user, book);
        if(favorited != null) {
            DAO.delete(favorited);
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
        BookWishlisted wishlisted = DAO.getSingle(BookWishlisted.class, "where user = ? and book = ? ", user, book);
        if(wishlisted != null)return;
        wishlisted = new BookWishlisted(user, book);
        DAO.put(wishlisted);
    }
    
    public static void unwishlistBook(String username, Long bookId) {
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        unwishlistBook(user, book);
    }
    
    private static void unwishlistBook(User user, Book book) {
        BookWishlisted wishlisted = DAO.getSingle(BookWishlisted.class, " where user = ? and book = ?", user, book);
        if(wishlisted != null) {
            DAO.delete(wishlisted);
        }
    }
    
    //finish reading (create posdta)
}
