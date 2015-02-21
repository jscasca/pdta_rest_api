package com.pd.api.entity.aux;

import com.pd.api.entity.NewBookRequest;
import com.pd.api.entity.User;

public class BookRequestWrapper {

    public String title;
    public String author;
    public String language;
    
    public BookRequestWrapper() {}
    public BookRequestWrapper(String title) {this(title, "", "");}
    public BookRequestWrapper(String title, String author) {this(title, author, "");}
    public BookRequestWrapper(String title, String author, String language) {
        this.title = title;
        this.author = author;
        this.language = language;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getLanguage() {
        return language;
    }
    
    public NewBookRequest getNewBookRequest(User user) {
        NewBookRequest nbr = new NewBookRequest(user, title, author, language);
        return nbr;
    }
}
