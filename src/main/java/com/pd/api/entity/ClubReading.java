package com.pd.api.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by tin on 14/10/18.
 *
 * A 'Reading' is how a club interacts with a book.
 * A 'Reading' can be a wishlisted book, a book that the club is reading or one that is has already finished
 */
@Entity
@Table(name="club_reading")
public class ClubReading {

    public enum ReadingStatus {
        WISHLISTED,
        READING,
        FINISHED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id = null;

    @ManyToOne(optional=false)
    @JoinColumn(name="club_id")
    protected Club club;

    @ManyToOne(optional=false)
    @JoinColumn(name="book_id")
    protected Book book;

    @ManyToOne(optional=false)
    @JoinColumn(name="user_id")
    protected User user; //Whoever suggested the reading in the first place

    @Type(type="timestamp")
    protected Date wishlisted = new Date(); //The date it was created

    @Type(type="timestamp")
    protected Date reading = null; //The date it was created

    @Type(type="timestamp")
    protected Date finished = null; //The date it was created

    @Enumerated
    protected ReadingStatus status;

    public ClubReading() {}
    public ClubReading(Club club, Book book, User user) {
        this.club = club;
        this.user = user;
        this.book = book;
        this.status = ReadingStatus.WISHLISTED;
    }

    public Date getWishlistedDate() {
        return wishlisted;
    }

    public Date getReadingDate() {
        return reading;
    }

    public Date getFinishedDate() {
        return finished;
    }

    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public Club getClub() {
        return club;
    }

    public Long getId() {
        return id;
    }

    public ReadingStatus getStatus() {
        return status;
    }

    public void updateToReading() {
        this.status = ReadingStatus.READING;
        this.reading = new Date();
    }

    public void updateToFinished() {
        this.status = ReadingStatus.FINISHED;
        this.finished = new Date();
    }
}
