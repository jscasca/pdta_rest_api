package com.pd.api.entity.aux;

import com.pd.api.entity.BookReading;
import com.pd.api.entity.Posdta;

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
    
    public void setRating(int rating) {
        this.rating = rating;
    }
    
    public Posdta getPosdta(BookReading reading) {
        return new Posdta(reading, posdta, rating);
    }
    
    @Override
    public String toString() {
        return rating + ":" + posdta;
    }
}