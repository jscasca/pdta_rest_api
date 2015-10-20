package com.pd.api.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Authors publish works, works are represented by books and book by publications that users can read
 * 
 * @author tin
 *
 */
@Entity
@Table(name="author")
public class Author implements Serializable {

    public static final String default_icon = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String name;
    
    private String icon;
    
    @Transient
    private String className = "Author";
    
    public Author() {}
    public Author(String name){ this(name, Author.default_icon);}
    public Author(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
    
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public String getClassName() {return className;}
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    @Override
    public String toString() {
        return name + ":" + id;
    }
}
