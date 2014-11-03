package com.pd.api.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Type;

@MappedSuperclass
public abstract class UserBookInteraction implements Serializable {

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
    private Date creationDate = new Date();
    
    protected UserBookInteraction() {}
    protected UserBookInteraction(User user, Book book) {
        setUser(user);
        setBook(book);
    }
    
    public User getUser() {
        return user;
    }
    
    public Book getBook() {
        return book;
    }
    
    public Work getWork() {
        return work;
    }
    
    public Date getCreationDate() {
        return creationDate;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setBook(Book book) {
        this.book = book;
        this.work = book.getWork();
    }
    
    @Override
    public String toString() {
        return id + ":" + user == null ? "null" : user.getUserName() + ":" + book == null ? "null" : book.getTitle();
    }
}
