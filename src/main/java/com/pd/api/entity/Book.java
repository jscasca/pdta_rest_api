package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Books are the representation in a specific language of an author's work
 * (For example: 'El Hobbit' would be the ES (spanish) representation of the work 'The Hobbit')
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
    
    private Work work;
    
    private String title;
    
    private Language language;
    
    private String icon;
    
    public Book(Work work){this(work, work.getTitle(), work.getLanguage(), work.getIcon());}
    public Book(Work work, String title, Language language) {this(work, title, language, work.getIcon());}
    public Book(Work work, String title, Language language, String icon) {
        this.work = work;
        this.title = title;
        this.language = language;
        this.icon = icon;
    }
    
    public Work getWork() {
        return work;
    }
    
    public String title() {
        return title;
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public String getIcon() {
        return icon;
    }
    
    @Override
    public String toString() {
        return title + ":" + id;
    }
}
