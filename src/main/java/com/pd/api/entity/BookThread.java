package com.pd.api.entity;

import javax.persistence.*;

/**
 * Created by tin on 29/11/17.
 */
@Entity
@Table(name="thread_book")
@PrimaryKeyJoinColumn(name="id")
public class BookThread extends CommentThread {

    @ManyToOne
    @JoinColumn(name="book_id")
    private Book book;

    @Transient
    private String className = "Book Thread";

    public BookThread() {}

    public BookThread(Book book) {
        super(ThreadType.BOOK);
        this.book = book;
    }

    public Book getBook(){
        return book;
    }
}
