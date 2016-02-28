package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="event_posdta")
@PrimaryKeyJoinColumn(name="id")
public class EventWithPosdta extends Event {

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;
    
    @ManyToOne
    @JoinColumn(name="posdta_id")
    private Posdta posdta;
    
    @Transient
    private String className = "Posdta Event";
    
    
    protected EventWithPosdta() {}
    
    public EventWithPosdta(User user, EventType type, Posdta posdta) {
        super(user, type);
        this.book = posdta.getBook();
        this.posdta = posdta;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setPosdta(Posdta posdta) {
        this.book = posdta.getBook();
        this.posdta = posdta;
    }
    
    public Posdta getPosdta() {
        return posdta;
    }
}
