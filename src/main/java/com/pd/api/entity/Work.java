package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * 
 * @author tin
 *
 */
@Entity
@Table(name="work")
public class Work {
    
    public static final String DEFAULT_ICON = "img/default.png";
    public static final String DEFAULT_THUMBNAIL = "img/defaultthumb.png";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @ManyToOne
    @JoinColumn(name="author_id")
    private Author author;
    
    private String title;
    
    private String icon;
    
    private String thumbnail;
    
    private Double rating = 0.0;
    
    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    @Transient
    private String className = "Work";
    
    public Work() {}
    public Work(Author author, String title, Language language) {this(author, title, DEFAULT_ICON, DEFAULT_THUMBNAIL, language);}
    public Work(Author author, String title, String icon, String thumbnail, Language language) {
        this.author = author;
        this.title = title;
        this.icon = icon;
        this.thumbnail = thumbnail;
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
    
    public String getThumbnail() {
        return thumbnail;
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
    
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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
