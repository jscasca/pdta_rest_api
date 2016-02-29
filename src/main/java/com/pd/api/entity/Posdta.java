package com.pd.api.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="posdta")
public class Posdta {

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
    private Date start = new Date();
    
    @Type(type="timestamp")
    private Date finish = new Date();
    
    private String posdta;
    
    private int rating;
    
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private PosdtaVoting votes;
    
    public Posdta() {}
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
    
    public PosdtaVoting getVotes() {
        return votes;
    }
    
    public void setVotes(PosdtaVoting votes) {
        this.votes = votes;
    }
    
    @Override
    public String toString() {
        return "id: " + id + "\nrating: " + rating + "\nposdta: " + posdta;
    }
}
