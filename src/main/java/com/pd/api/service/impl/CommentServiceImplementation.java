package com.pd.api.service.impl;

import com.pd.api.db.DAO;
import com.pd.api.entity.*;
import com.pd.api.entity.aux.CommentTree;
import com.pd.api.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by tin on 29/11/17.
 */
public class CommentServiceImplementation {

    private static final Logger LOG = LoggerFactory.getLogger(BookServiceImplementation.class);

    private static final int CHAR_LIMIT = 6400;

    public static Comment startBookThread(String username, Long bookId, String text){
        //Take a book
        if(text == "") {
            throw new InvalidParameterException("The content cannot be left empty");
        }
        if(text.length() > CHAR_LIMIT) {
            throw new InvalidParameterException("The content cannot have more than 6,400 characters");
        }
        User user = DAO.getUserByUsername(username);
        Book book = DAO.get(Book.class, bookId);
        Comment comment = new Comment(user, text);
        //create a comment
        comment = DAO.put(comment);
        ThreadForBook thread = new ThreadForBook(comment, book);
        //Create a thread
        thread = DAO.put(thread);
        //save
        return thread.getComment();

    }

    public static Comment reply(String username, Long commentId, String text) {
        User user = DAO.getUserByUsername(username);
        Comment parent = DAO.get(Comment.class, commentId);
        Comment reply = new Comment(user, text, parent);
        return DAO.put(reply);
    }

    public static CommentTree getBookCommentTree(Long bookId) {
        Book book = DAO.getBookById(bookId);
        return DAO.getBookCommentTree(book);
    }

    public static List<CommentThread> getBookThreads(Long bookId, int first, int limit) {
        Book book = DAO.getBookById(bookId);
        return DAO.getBookThreads(book, first, limit);
    }
}
