package com.pd.api.entity.aux;

import com.pd.api.entity.Book;
import com.pd.api.entity.BookReading;
import com.pd.api.entity.Posdta;
import com.pd.api.entity.User;
import org.apache.commons.lang3.StringUtils;

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

    /*
    We better check if the posdta is null, is empty
     */
    public String getPosdta() {
        return StringUtils.isBlank(posdta) ? null : posdta;
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

    public Boolean isRatingOnly() { return hasRating() && !hasPosdta(); }

    public Boolean isPosdtaOnly() { return !hasRating() && hasPosdta(); }

    public Boolean isRatingAndPosdta() { return hasRating() && hasPosdta(); }

    public Boolean hasRating() { return rating > 0;}

    public Boolean hasPosdta() { return !StringUtils.isBlank(posdta);}
    
    @Override
    public String toString() {
        return rating + ":" + posdta;
    }
}
