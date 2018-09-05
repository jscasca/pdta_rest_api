package com.pd.api.entity.aux;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;

import java.util.List;

/**
 * Created by tin on 25/08/18.
 */
public class UserProfile {

    private User user;
//    private int readingCount;
//    private int wishlistCount;
//    private int readCount;
    private List<Book> readingBooks;
    private List<Book> wishlistBooks;
    private List<Book> favouriteBooks;
    private List<Posdta> rated;

    public UserProfile(User user) {
        this.user = user;
        readingBooks = DAO.getUserReading(user.getId(), 0, 0);
        wishlistBooks = DAO.getUserWishlisted(user.getId(), 0, 0);
        favouriteBooks = DAO.getUserFavorites(user.getId(), 0, 0);
        rated = DAO.getUserPosdtas(user.getId(), 0, 0);
    }

    public User getUser() { return user;}
//    public int getReadingCount() {return readingCount;}
//    public int getWishlistCount() {return wishlistCount;}
//    public int getReadCount() {return readCount;}
//    public int getPrologesCount() {return prologesCount;}
    public List<Book> getReadingBooks() { return readingBooks; }
    public List<Book> getWishlistBooks() { return wishlistBooks; }
    public List<Book> getFavouriteBooks() { return favouriteBooks; }
    public List<Posdta> getRated() { return rated; }
//    public List<Posdta> getReviews() { return reviews; }
}
