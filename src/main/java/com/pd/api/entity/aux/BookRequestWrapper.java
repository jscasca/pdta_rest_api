package com.pd.api.entity.aux;

import com.pd.api.entity.NewBookRequest;
import com.pd.api.entity.User;
import com.pd.api.entity.Work;

public class BookRequestWrapper {

    public String title;
    
    public String author;
    
    public long authorId;
    
    public long workId;
    
    public String language;
    
    public String icon;
    
    public String thumbnail;
    
    public BookRequestWrapper() {}
    public BookRequestWrapper(String title, String author, String language, long authorId, long workId, String icon, String thumbnail) {
        this.title = title;
        this.author = author;
        this.language = language;
        this.authorId = authorId;
        this.workId = workId;
        this.icon = icon;
        this.thumbnail = thumbnail;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getAuthorString() {
        return author;
    }
    
    public long getauthorId() {
        return authorId;
    }
    
    public long getWorkId() {
        return workId;
    }
    
    public String getIcon() { if("".equals(icon))return Work.DEFAULT_ICON;else return icon;}
    public String getThumbnail() {if("".equals(thumbnail)) return Work.DEFAULT_THUMBNAIL; else return thumbnail;}
    
    public String getLanguageString() {
        return language;
    }
    
    public boolean hasNewAuthor() {
        return authorId == -1;
    }
    
    public boolean hasNewWork() {
        return workId == -1;
    }
    
    public NewBookRequest getNewBookRequest(User user) {
        NewBookRequest nbr = new NewBookRequest(user, title, author, language);
        return nbr;
    }
}
