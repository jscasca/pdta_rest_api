package com.pd.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.codehaus.jackson.annotate.JsonManagedReference;

import javax.persistence.*;
import java.util.Set;

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
    
    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="author_book", joinColumns=@JoinColumn(name="book_id"), inverseJoinColumns=@JoinColumn(name="author_id"))
    private Set<Author> authors;
    
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
    public Work(Set<Author> authors, String title, Language language) {this(authors, title, DEFAULT_ICON, DEFAULT_THUMBNAIL, language);}
    public Work(Set<Author> authors, String title, String icon, String thumbnail, Language language) {
        this.authors = authors;
        this.title = title;
        this.icon = icon;
        this.thumbnail = thumbnail;
        this.language = language;
        this.rating = 0.0;
    }
    
    public Long getId() {
        return id;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public String getAuthorsNames() {
        StringBuilder sb = new StringBuilder();
        for(Author author : this.authors) {
            sb.append(author.getName());
            sb.append(" ");
        }
        return sb.toString();
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
    
    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
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
    
    @Override
    public String toString() {
        return title + ":" + id;
    }
}
