package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
    
    private String title;
    
    private String icon;
    
    @ManyToOne
    @JoinColumn(name="language_id")
    private Language language;
    
    public Book() {}
    public Book(Work work) {this(work, work.getTitle(), work.getIcon(), work.getLanguage());} 
    public Book(Work work, String title, Language language) {this(work, title, work.getIcon(), language);}
    public Book(Work work, String title, String icon, Language language) {
        this.work = work;
        this.title = title;
        this.icon = icon;
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
    
    public Language getLanguage() {
        return language;
    }
    
    public void setWork(Work work) {
        this.work = work;
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
    
    @Override
    public String toString() {
        return title + ":" + id;
    }
}
