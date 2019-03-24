package com.pd.api.entity.aux;

import com.pd.api.entity.Work;

public class BookRequestWrapper {

    public String title;
    
    public String authors;
    
    public String language;
    
    public String icon;
    
    public String thumbnail;

    public String details;
    
    public BookRequestWrapper() {}
    public BookRequestWrapper(String title, String authors, String language, String icon, String thumbnail, String details) {
        this.title = title;
        this.authors = authors;
        this.language = language;
        this.icon = icon;
        this.thumbnail = thumbnail;
        this.details = details;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String[] getAuthors() {
        return authors.split(";");
    }
    
    public String getIcon() { if("".equals(icon))return Work.DEFAULT_ICON;else return icon;}
    public String getThumbnail() {if("".equals(thumbnail)) return Work.DEFAULT_THUMBNAIL; else return thumbnail;}
    
    public String getLanguageString() {
        return language;
    }

    public String getDetails() {
        return details;
    }
}
