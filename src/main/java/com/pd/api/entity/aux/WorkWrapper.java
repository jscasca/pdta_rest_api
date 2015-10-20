package com.pd.api.entity.aux;

import com.pd.api.db.DAO;
import com.pd.api.entity.Author;
import com.pd.api.entity.Language;
import com.pd.api.entity.Work;
import com.pd.api.exception.BadRequestException;

public class WorkWrapper {

    public String title;
    
    public String icon = "";
    
    public String language;
    
    public WorkWrapper() {}
    public WorkWrapper(String title, String icon, String language) {
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
    
    public Work getWork(Author author) {
        Language lang = DAO.getLanguageByCode(language);
        if(lang == null) throw new BadRequestException("The requested language code [" + language + "] does not exist");
        return new Work(author, title, icon == "" ? Work.DEFAULT_ICON : icon, icon == "" ? Work.DEFAULT_THUMBNAIL : icon, lang);
    }
}
