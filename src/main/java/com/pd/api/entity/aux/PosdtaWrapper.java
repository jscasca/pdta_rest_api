package com.pd.api.entity.aux;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookReading;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;

public class PosdtaWrapper {

    public String posdta;
    public int rating;
    
    public PosdtaWrapper() {}
    public PosdtaWrapper(String posdta, int rating) {
        this.posdta = posdta;
        if(rating > 5) this.rating = 5;
        else if(rating < 1) this.rating = 1;
        else this.rating = rating;
    }
    
    public String getPosdta() {
        return posdta;
    }
    
    public int getRating() {
        return rating;
    }
    
    public void setPosdta(String posdta) {
        this.posdta = posdta;
    }
    
    public Posdta getPosdta(BookReading reading) {
        return new Posdta(reading, posdta, rating);
    }
    
    public Posdta getPosdta(User user, Book book) {
        return new Posdta(user, book, posdta, rating);
    }

    public Boolean isRatingOnly() { return rating > 0 && posdta == null; }

    public Boolean isPosdtaOnly() { return rating == 0 && posdta != null; }

    public Boolean isRatingAndPosdta() { return rating > 0 && posdta != null; }
    
    @Override
    public String toString() {
        return rating + ":" + posdta;
    }
}
