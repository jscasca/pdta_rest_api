package com.pd.api.entity.aux;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.BookFavorited;
import com.pd.api.entity.BookReading;
import com.pd.api.entity.BookWishlisted;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;

public class UserToBook {

    private boolean wishlisted;
    private boolean favorite;
    private boolean reading;
    private Posdta posdta;
    
    private User user;
    private Book book;
    
    public UserToBook() {}
    public UserToBook(User user, Book book) {
        this.user = user;
        this.book = book;
        updateReading();
        updatePosdta();
        updateWishlist();
        updateFavorite();
    }
    
    public boolean isReading() {
        return reading;
    }
    
    public boolean isFavorite() {
        return favorite;
    }
    
    public boolean isWishlisted() {
        return wishlisted;
    }
    
    public boolean hasPosdta() {
        return posdta == null;
    }
    
    public Posdta getPosdta() {
        return posdta;
    }
    
    private void updateReading() {
        BookReading bookReading = DAO.getUnique(BookReading.class, "where user = ? and book = ?", user, book);
        if(bookReading != null) reading = true;
        else reading = false;
    }
    
    private void updateWishlist() {
        BookWishlisted bookWishlisted = DAO.getUnique(BookWishlisted.class, "where user = ? and book = ?", user, book);
        if(bookWishlisted != null) wishlisted = true;
        else wishlisted = false;
    }
    
    private void updateFavorite() {
        BookFavorited bookFavorited = DAO.getUnique(BookFavorited.class, "where user = ? and book = ? ", user, book);
        if(bookFavorited != null) favorite = true;
        else favorite = false;
    }
    
    private void updatePosdta() {
        posdta = DAO.getUnique(Posdta.class, "where user = ? and book = ?", user, book);
    }
    
    public String toString() {
        return "reading: " + reading + "\nfavorite: " + favorite + "\nwishlist: " + wishlisted + "\nPosdta: " + posdta;
    }
}
