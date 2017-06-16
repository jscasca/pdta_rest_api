package com.pd.api.entity.aux;

import com.pd.api.entity.Book;

public class BookInfo {
    
    private Book book;
    
    private String className = "BookInfo";

    public BookInfo() {
        
    }
    
    public BookInfo(Book book) {
        this.book = book;
    }
    
    public Book getBook(){return book;}
    public String getClassName(){return className;}
    
}
