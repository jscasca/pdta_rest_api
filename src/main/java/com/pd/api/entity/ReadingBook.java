 package com.pd.api.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

public class ReadingBook {

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
    
    @Type(type="timestamp")
    private Date listDate;
    
    public ReadingBook() {}
    public ReadingBook(User user, Book book) {
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
    
    public Date getListedDate() {
        return listDate;
    }
    
    @Override
    public String toString() {
        return user.getUserName() + ":" + book.getTitle();
    }
}
