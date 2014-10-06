package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.AuthorWrapper;
import com.pd.api.entity.aux.WorkWrapper;

public class AuthorServiceImplementation {

    public static List<Author> getAuthors(int first, int limit) {
        return DAO.getAll(Author.class, first, limit);
    }
    
    public static Author getAuthorById(Long id) {
        return DAO.get(Author.class, id);
    }
    
    public static List<Work> getAuthorWorks(Long id, int first, int limit) {
        //TODO: implement
        Author author = DAO.get(Author.class, id);
        return DAO.getAll(Work.class, " where author = ? ", "", first, limit, author);
    }
    
    public static Author createAuthor(AuthorWrapper authorWrapper) {
      //TODO: implement validations of some sort
        Author author = authorWrapper.getAuthor();
        //Future validations
        DAO.put(author);
        return author;
    }
    
    public static Work createWork(Long id, WorkWrapper workWrapper) {
        //TODO: implement validations of some sort
        Author author = DAO.get(Author.class, id);
        Work work = workWrapper.getWork(author);
        DAO.put(work);
        Book book = work.createBook();
        DAO.put(book);
        return work;
    }
}
