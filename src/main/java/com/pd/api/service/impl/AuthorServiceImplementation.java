package com.pd.api.service.impl;

import java.util.List;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.AuthorWrapper;
import com.pd.api.entity.aux.WorkWrapper;
import com.pd.api.exception.DuplicateResourceException;

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
        List<Author> possibleByName = SearchServiceImplementation.searchAuthors(authorWrapper.getName(), 0, 2);
        if(possibleByName.size() > 0) {
            //Maybe more validations here
            throw new DuplicateResourceException("Duplicate Author Name","The author you are trying to create might already exist: [" + possibleByName.get(0) + "]","Author Name");
        }
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
