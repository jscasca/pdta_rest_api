package com.pd.api.entity.aux;

import com.pd.api.entity.Author;

public class AuthorWrapper {

    public String name;
    
    public String icon = "";
    
    public AuthorWrapper() {}
    public AuthorWrapper(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
    
    public String getName() {
        return name;
    }
    
    public Author getAuthor() {
        return new Author(name, icon == "" ? Author.default_icon : icon);
    }
}
