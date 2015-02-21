package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.BookWrapper;

public class WorkServiceImplementation {

    public static List<Work> getWorks(int start, int limit) {
        return DAO.getAll(Work.class, start, limit);
    }
    
    public static Work getWorkById(Long id) {
        return DAO.get(Work.class, id);
    }
    
    public static List<Book> getBooksByWork(Long id, int start, int limit) {
        Work work = DAO.get(Work.class, id);
        return DAO.getAll(Book.class, " where work = ? ", "", start, limit, work);
    }
    
    public static Book createBook(Long id, BookWrapper bookWrapper) {
        Work work = DAO.get(Work.class, id);
        Book book = bookWrapper.getBook(work);
        DAO.put(book);
        return book;
    }
}
