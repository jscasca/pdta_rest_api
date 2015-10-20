package com.pd.api.entity.aux;

import com.pd.api.db.DAO;
import com.pd.api.entity.Book;
import com.pd.api.entity.Language;
import com.pd.api.entity.Work;
import com.pd.api.exception.BadRequestException;

public class BookWrapper {

    public String title;
    
    public String icon = "";
    
    public String language;
    
    public BookWrapper() {}
    public BookWrapper(String title, String icon, String language) {
        this.title = title;
        this.icon = icon;
        this.language = language;
    }
    
    public String getTitle() {
        return title;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public String getLanguageCode() {
        return language;
    }
    
    public Book getBook(Work work) throws BadRequestException {
        Language lang = DAO.getLanguageByCode(language);
        if(lang == null) throw new BadRequestException("The requested language code [" + language + "] does not exist");
        return new Book(work, title, icon == "" ? work.getIcon() : icon,icon == "" ? work.getIcon() : icon, lang);
    }
}
