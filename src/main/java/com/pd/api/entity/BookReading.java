 package com.pd.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name="book_reading", uniqueConstraints=@UniqueConstraint(columnNames={"user_id", "book_id"}))
public class BookReading extends UserBookInteraction {
    
    public BookReading() {}
    public BookReading(User user, Book book) {
        super(user, book);
    }
}
