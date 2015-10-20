package com.pd.api.entity.aux;

import java.util.ArrayList;
import java.util.List;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookRating;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;

public class UserInfo {
    
    public static final int DEFAULT_LIMIT = 10;
    
    private User user;
    private List<Book> reading = new ArrayList<Book>();
    private List<Book> wishlisted = new ArrayList<Book>();
    private List<Book> favorited = new ArrayList<Book>();
    private List<Posdta> posdtas = new ArrayList<Posdta>();
    //Users who USER is following
    private List<User> following = new ArrayList<User>();
    //Users who are following USER
    private List<User> followers = new ArrayList<User>();
    
    private int followerCount = 0;
    private int followingCount = 0;
    
    private String className = "UserInfo";

    public UserInfo() {
        
    }
    
    public UserInfo(User user) {
        this.user = user;
        //TODO additionally put here personal info or something else
        //This is just the basic idea
    }
    
    public UserInfo(User user, List<Book> reading, List<Book> wishlisted, List<Book> favorited, List<Posdta> posdtas, int followerCount, int followingCount) {
        this(user);
        this.reading = reading;
        this.wishlisted = wishlisted;
        this.favorited = favorited;
        this.posdtas = posdtas;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }
    
    public void setReadingList(List<Book> readingList) { this.reading = readingList;}
    public void setWishList(List<Book> wishList) { this.wishlisted = wishList;}
    public void setFavoriteList(List<Book> favoriteList) { this.favorited = favoriteList;}
    public void setPosdtas(List<Book> posdtaList) { this.reading = posdtaList;}
    public void setFollowers(List<User> followerList) { this.followers = followerList;}
    public void setFollowing(List<User> followingList) { this.followers = followingList;}
    
    public User getUser() {return user;}
    public List<Book> getBooksReading() {return reading;}
    public List<Book> getBooksWishlisted() {return wishlisted;}
    public List<Book> getBooksFavorited() {return favorited;}
    public List<Posdta> getPosdtas() {return posdtas;}
    public List<User> getFollowers() {return followers;}
    public List<User> getFollowing() {return following;}
    public int getFollowerCount() {return followerCount;}
    public int getFollowingCount() {return followingCount;}
    public String getClassName() {return className;}
    
}
