package com.pd.api.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name="book_detail")
public class BookDetail {

    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private Book book;

    @Type(type="text")
    private String details;

    public BookDetail() {}

    public BookDetail(Book book, String detail) {
        this.book = book;
        this.details = detail;
        this.id = book.getId();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
