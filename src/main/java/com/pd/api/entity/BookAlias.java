package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="book_alias")
public class BookAlias {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String title;
    
    private Language language;
    
    public BookAlias(Book book){this(book.getTitle(), book.getLanguage());}
    public BookAlias(String title, Language language) {
        this.title = title;
        this.language = language;
    }
    
    public String getTitle() {
        return title;
    }
    
    public Language getLanguage() {
        return language;
    }
}
