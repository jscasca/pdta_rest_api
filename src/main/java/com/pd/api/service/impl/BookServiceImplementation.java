package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;

public class BookServiceImplementation {

    public static List<Book> getBooks(int start, int limit) {
        return DAO.getAll(Book.class, start, limit);
    }
    
    public static Book getBookById(Long id) {
        return DAO.get(Book.class, id);
    }
    
    //get posdtas by book
    
    //get favoritees by book
    
    //get reading by book
    
    //favorite book
    
    //start reading
    
    //finish reading (create posdta)
}
