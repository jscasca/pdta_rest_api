package com.pd.api.entity;

import javax.persistence.*;

/**
 * Created by tin on 29/11/17.
 */
@Entity
@Table(name="thread_book")
@PrimaryKeyJoinColumn(name="id")
public class ThreadForBook extends CommentThread {

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @Transient
    private String className = "Book Thread";

    public ThreadForBook(){
        super();
    }

    public ThreadForBook(Comment comment, Book book) {
        super(comment);
        this.book = book;
    }

    public Book getBook(){
        return book;
    }
}
