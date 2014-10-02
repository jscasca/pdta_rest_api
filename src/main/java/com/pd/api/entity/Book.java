package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    
    public static final String default_icon = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private Author author;
    
    private String title;
    
    private String icon;
    
    private Language language;
    
    public Book(Author author, String title, Language language) {this(author, title, Book.default_icon, language);}
    public Book(Author author, String title, String icon, Language language) {
        this.author = author;
        this.title = title;
        this.icon = icon;
        this.language = language;
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
    
    public Language getLanguage() {
        return language;
    }
    
    @Override
    public String toString() {
        return title + ":" + id;
    }
}
