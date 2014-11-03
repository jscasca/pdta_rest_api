package com.pd.api.entity.aux;

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
}
