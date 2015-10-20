package com.pd.api.entity.aux;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookRating;

public class BookInfo {
    
    private Book book;
    private BookRating ratings;
    
    private String className = "BookInfo";

    public BookInfo() {
        
    }
    
    public BookInfo(BookRating ratings, Book book) {
        this.book = book;
        this.ratings = ratings;
    }
    
    public Book getBook(){return book;}
    public BookRating getRatings(){return ratings;}
    public String getClassName(){return className;}
    
}
