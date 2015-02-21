package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="new_book_request")
public class NewBookRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    /**
     * The user who requested the book
     */
    @ManyToOne
    @JoinColumn(name="author_id")
    private User user;
    
    private String title;
    
    private String author;
    
    private String language;
    
    public NewBookRequest() {}
    public NewBookRequest(User user, String title, String author, String language) {
        this.user = user;
        this.title = title;
        this.author = author;
        this.language = language;
    }
    
    public Long getId() {
        return id;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getLanguage() {
        return language;
    }
}
