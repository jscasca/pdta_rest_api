package com.pd.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

@Entity
@Table(name="posdta")
public class Posdta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = null;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    protected User user;
    
    @ManyToOne
    @JoinColumn(name="work_id")
    protected Work work;
    
    @ManyToOne
    @JoinColumn(name="book_id")
    protected Book book;
    
    @Type(type="timestamp")
    protected Date start = new Date();
    
    @Type(type="timestamp")
    protected Date finish = new Date();
    
    protected String posdta = null;
    
    protected int rating;

    @Formula("(SELECT COUNT(*) FROM user_vote v WHERE v.posdta_id = id)")
    protected int votes;
    
    @Transient
    protected String className = "Posdta";
    
    public Posdta() {}
    public Posdta(BookReading reading, int rating) {
        this(reading, null, rating);
    }
    public Posdta(BookReading reading, String posdta, int rating) {
        this(reading.getUser(), reading.getWork(), reading.getBook(), reading.getCreationDate(), posdta, rating);
    }
    public Posdta(User user, Work work, Book book, Date start, String posdta, int rating) {
        this.user = user;
        this.work = work;
        this.book = book;
        this.start = start;
        this.posdta = posdta;
        this.rating = rating;
    }
    public Posdta(User user, Book book, String posdta, int rating) {
        this.user = user;
        this.work = book.getWork();
        this.book = book;
        this.posdta = posdta;
        this.rating = rating;
    }

    public void setPosdta(String posdta) {
        this.posdta = posdta;
    }
    
    public Long getId() {
        return id;
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
    
    public Date getStart() {
        return start;
    }
    
    public Date getFinish() {
        return finish;
    }
    
    public String getPosdta() {
        return posdta;
    }
    
    public int getRating() {
        return rating;
    }

    public String getClassName() {
        return className;
    }

    public int getVotes() {
        return votes;
    }

    /*public int getUpvotes() {
        return upvotes;
    }

    public int getDownvotes() {
        return downvotes;
    }*/

    
    @Override
    public String toString() {
        return "id: " + id + "\nrating: " + rating + "\nposdta: " + posdta;
    }
}
