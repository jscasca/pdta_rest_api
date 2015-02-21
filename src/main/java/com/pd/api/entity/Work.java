package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * 
 * @author tin
 *
 */
@Entity
@Table(name="work")
public class Work {

    public static final String default_icon = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;
    
    private String title;
    
    private String icon;
    
    private Double rating = 0.0;
    
    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    public Work() {}
    public Work(Author author, String title, Language language) {this(author, title, Work.default_icon, language);}
    public Work(Author author, String title, String icon, Language language) {
        this.author = author;
        this.title = title;
        this.icon = icon;
        this.language = language;
        this.rating = 0.0;
    }
    
    public Long getId() {
        return id;
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public double getRating() {
        return rating;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public void setLanguage(Language language) {
        this.language = language;
    }
    
    public Book createBook() {
        return new Book(this);
    }
    
    public void updateRating(WorkRating wr) {
        //TODO: implement
        this.rating = wr.getAverage();
    }
    
    @Override
    public String toString() {
        return title + ":" + id;
    }
}
