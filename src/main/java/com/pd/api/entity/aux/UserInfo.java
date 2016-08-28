package com.pd.api.entity.aux;

import java.util.ArrayList;
import java.util.List;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookRating;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;

public class UserInfo {
    
    public static final int DEFAULT_LIMIT = 3;
    public static final int DEFAULT_POSDTA_LIMIT = 10;
    
    private User user;
    
    private int followers = 0;
    private int following = 0;
    private int reading = 0;
    private int wishlisted = 0;
    private int favorited = 0;
    private int posdtas = 0;
    
    private String className = "UserInfo";

    public UserInfo() {
        
    }
    
    public UserInfo(User user) {
        this.user = user;
        //TODO additionally put here personal info or something else
        //This is just the basic idea
    }
    
    public UserInfo(User user, int reading, int wishlisted, int favorited, int posdtas, int followerCount, int followingCount) {
        this(user);
        this.reading = reading;
        this.wishlisted = wishlisted;
        this.favorited = favorited;
        this.posdtas = posdtas;
        this.followers = followerCount;
        this.following = followingCount;
    }
    
    public User getUser() {return user;}
    public int getBooksReading() {return reading;}
    public int getBooksWishlisted() {return wishlisted;}
    public int getBooksFavorited() {return favorited;}
    public int getPosdtas() {return posdtas;}
    public int getFollowers() {return followers;}
    public int getFollowing() {return following;}
    public String getClassName() {return className;}
    
}
