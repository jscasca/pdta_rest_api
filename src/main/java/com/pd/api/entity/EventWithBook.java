package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="event_book")
@PrimaryKeyJoinColumn(name="id")
public class EventWithBook extends Event {

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
    
    @Transient
    private String className = "Book Event";
    
    protected EventWithBook() {}
    
    public EventWithBook(User user, EventType type, Book book) {
        super(user, type);
        this.book = book;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
}
