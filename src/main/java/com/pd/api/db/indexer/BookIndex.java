package com.pd.api.db.indexer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="index_book")
public class BookIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @Column(name="last_book_indexed", nullable=false)
    private Long lastBookIndexed;
    
    @Column(name="books_indexed")
    private int booksIndexed;
    
    @Type(type="timestamp")
    @Column(name="indexed_on")
    private Date indexedOn = new Date();
    
    public BookIndex() {}
    public BookIndex(Long id, int indexed) {
        lastBookIndexed = id;
        booksIndexed = indexed;
    }
    
    public Long getLastIndexed() {
        return lastBookIndexed;
    }
    
    public void setLastIndexed(Long id) {
        lastBookIndexed = id;
    }
    
    public void setBooksIndexed(int indexed) {
        booksIndexed = indexed;
    }
}
