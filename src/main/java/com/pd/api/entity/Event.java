package com.pd.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Index;
import org.hibernate.annotations.Type;

/**
 * An event is what users see regarding the people they are following
 * Events are interactions that users are part of such as
 *  USER started reading BOOK
 *  USER left a posdta for BOOK
 *  USER started following USER
 *  USER got achievement ACHIEVEMENT
 * 
 * @author tin
 *
 */
@Entity
@Table(name="event")
@Inheritance(strategy=InheritanceType.JOINED)
public class Event {

    public enum EventType { 
        STARTED_READING, 
        FINISHED_READING, 
        STOPPED_READING,
        STARTED_FOLLOWING,
        STOPPED_FOLLOWING,
        POSDTA,
        RATED,
        WISHLISTED,
        SUGGESTED,
        FAVORITED,
        ADDED,
        NULL
    };
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @Index(name="event_user_index")
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    @Type(type="timestamp")
    private Date eventDate = new Date();
    
    @Enumerated(EnumType.STRING)
    private EventType type;
    
    protected Event() {}
    //Event type
    protected Event(User user, EventType type) {
        this.user = user;
        this.type = type;
    }
    
    public User getUser() {
        return user;
    }
    
    public Date getEventDate() {
        return eventDate;
    }
    
    public EventType getEventType() {
        return type;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public void setType(EventType type) {
        this.type = type;
    }
}
