package com.pd.api;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Book;
import com.pd.api.entity.BookSuggestions;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Language;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.Work;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.service.impl.BookServiceImplementation;
import com.pd.api.service.impl.SearchServiceImplementation;
import com.pd.api.util.LuceneIndexer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ApplicationTest {

    public static void main(String[] args) throws IOException {
        //testFavoritesList();
        //testSearch();
        //testMostRead();
        testSuggestions();
    }
    
    public static void testSuggestions() {
        User user = DAO.getUserById(7L);
        BookServiceImplementation.updateSuggestions(user.getUserName());
    }
    
    public static void testMostRead() {
        List<Book> mostRead = BookServiceImplementation.getMostRead(0, 10);
        System.out.println(mostRead);
        
    }
    
    public static void testFavoritesList() {
        List<User> usersThatFavorited = BookServiceImplementation.getBookFavorites(2L, 0, 10);
        System.out.println(usersThatFavorited);
    }
    
    public static void testSearch() throws IOException {
        //index first
        LuceneIndexer.index();
        System.out.println("done indexing");
        List<Book> bookRetrieved = SearchServiceImplementation.searchBooks("sputnik", 0, 10);
        System.out.println(bookRetrieved);
    }
}
