package com.pd.api.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tin on 25/06/17.
 */
@Entity
@Table(name="book_request")
public class BookRecordRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;

    //time requested
    @Type(type="timestamp")
    private Date eventDate = new Date();

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public BookRecordRequest(){}

    public BookRecordRequest(User user, Book book) {
        this.book = book;
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
