package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Works are done by author and are represented by books in varios languages
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
    
    private Author author;
    
    private String title;
    
    private Language language;
    
    private String icon;
    
    public Work(Author author, String title, Language language) { this(author, title, language, Work.default_icon);}
    public Work(Author author, String title, Language language, String icon) {
        this.author = author;
        this.title = title;
        this.language = language;
        this.icon = icon;
    }
    
    public Author getAuthor() {
        return author;
    }
    
    public String getTitle() {
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
