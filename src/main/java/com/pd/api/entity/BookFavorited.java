package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="book_favorited", uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "book_id"}))
public class BookFavorited extends UserBookInteraction {
    
    public BookFavorited() {}
    public BookFavorited(User user, Book book) {
        super(user, book);
    }
}
