package com.pd.api.entity;

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
import javax.persistence.Transient;

/**
 * Books are the main writing of an author
 * 
 * @author tin
 *
 */
@Entity
@Table(name="book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @ManyToOne
    @JoinColumn(name="work_id")
    private Work work;
    
    @OneToOne(cascade=CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private BookRating rating;
    
    private String title;
    
    private String icon;
    
    private String thumbnail;
    
    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    @Transient
    private String className = "Book";
    
    public Book() {}
    public Book(Work work) {this(work, work.getTitle(), work.getIcon(), work.getThumbnail(), work.getLanguage());} 
    public Book(Work work, String title, Language language) {this(work, title, work.getIcon(), work.getThumbnail(), language);}
    public Book(Work work, String title, String icon, String thumbnail, Language language) {
        this.work = work;
        this.title = title;
        this.icon = icon;
        this.thumbnail = thumbnail;
        this.language = language;
    }
    
    public Work getWork() {
        return work;
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
    
    public Long getId() {
        return id;
    }
    
    public Author getAuthor() {
        return this.work.getAuthor();
    }
    
    public String getAuthorName() {
        return this.work.getAuthor().getName().toString();
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public BookRating getRating() { return rating;}
    
    public String getClassName() { return className;}
    
    public void setWork(Work work) {
        this.work = work;
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
    
    public void setRating(BookRating rating) {
        this.rating = rating;
    }
    
    @Override
    public String toString() {
        return title + ":" + id;
    }
}
