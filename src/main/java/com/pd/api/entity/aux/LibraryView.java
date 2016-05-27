package com.pd.api.entity.aux;

import java.util.ArrayList;
import java.util.List;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookRating;
import com.pd.api.entity.Posdta;

public class LibraryView {
    
    public static final int DEFAULT_LIBRARY_VIEW_LIMIT = 3;
    public static final int DEFAULT_POSDTA_VIEW_LIMIT = 6;
    
    private List<Book> reading = new ArrayList<Book>();
    private List<Book> wishlisted = new ArrayList<Book>();
    private List<Book> favorited = new ArrayList<Book>();
    private List<Posdta> posdtas = new ArrayList<Posdta>();
    
    private String className = "LibraryView";

    public LibraryView() {
        
    }
    
    public void setReading(List<Book> books){ this.reading = books;}
    public void setWishlisted(List<Book> books){ this.wishlisted = books;}
    public void setFavorited(List<Book> books){ this.favorited = books;}
    public void setPosdtas(List<Posdta> posdtas){ this.posdtas = posdtas;}
    
    public List<Book> getReading(){return reading;}
    public List<Book> getWishlisted(){return wishlisted;}
    public List<Book> getFavorited(){return favorited;}
    public List<Posdta> getPosdtas(){return posdtas;}
    public String getClassName(){return className;}
    
    /*TODO: public String toString() {
        return "";
    }*/
    
}
