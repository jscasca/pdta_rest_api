package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="book_wishlisted", uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "book_id"}))
public class BookWishlisted extends UserBookInteraction {
    
    public BookWishlisted() {}
    public BookWishlisted(User user, Book book) {
        super(user, book);
    }
}
