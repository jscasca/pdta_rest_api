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
@Table(name="index_author")
public class AuthorIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @Column(name="last_author_indexed", nullable=false)
    private Long lastAuthorIndexed;
    
    @Column(name="authors_indexed")
    private int authorsIndexed;
    
    @Type(type="timestamp")
    @Column(name="indexed_on")
    private Date indexedOn = new Date();
    
    public AuthorIndex() {}
    public AuthorIndex(Long id, int indexed) {
        lastAuthorIndexed = id;
        authorsIndexed = indexed;
    }
    
    public Long getLastIndexed() {
        return lastAuthorIndexed;
    }
    
    public void setLastIndexed(Long id) {
        lastAuthorIndexed = id;
    }
    
    public void setBooksIndexed(int indexed) {
        authorsIndexed = indexed;
    }
}
