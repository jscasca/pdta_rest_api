package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="favorited_book")
public class FavoritedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name="work_id")
    private Work work;
    
    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
    
    public FavoritedBook() {}
    public FavoritedBook(User user, Book book) {
        this.user = user;
        this.work = book.getWork();
        this.book = book;
    }
    
    public User getUser() {
        return user;
    }
    
    public Work getWork() {
        return work;
    }
    
    public Book getBook() {
        return book;
    }
    
    @Override
    public String toString() {
        return user.getUserName() + ":" + book.getTitle();
    }
}