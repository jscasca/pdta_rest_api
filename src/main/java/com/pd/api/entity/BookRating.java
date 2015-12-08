package com.pd.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name="book_rating")
public class BookRating {

    @Id
    @Column(name="book_id")
    private Long id;
    
    private int rated1;
    private int rated2;
    private int rated3;
    private int rated4;
    private int rated5;
    private double rated = 0;
    private int reading;
    private int wishlisted;
    private int favorited;
    
    public BookRating() {}
    public BookRating(Long bookId) {this(bookId, 0, 0, 0, 0, 0, 0, 0, 0);}
    public BookRating(Long bookId, int r1, int r2, int r3, int r4, int r5, int ring, int wish, int fav) {
        id = bookId;
        rated1 = r1;
        rated2 = r2;
        rated3 = r3;
        rated4 = r4;
        rated5 = r5;
        calculateRating();
        reading = ring;
        wishlisted = wish;
        favorited = fav;
    }
    
    private void calculateRating() {
        if((rated1 + rated2 + rated3 + rated4 + rated5)>=1)
        rated = ((rated1 + (rated2*2) + (rated3*3) + (rated4*4) + (rated5*5))/(double)(rated1 + rated2 + rated3 + rated4 + rated5));
    }
    
    public void addReading() { reading++; }
    public void removeReading() { reading--;if(reading<0)reading=0; }
    public void addWishlisted() { wishlisted++; }
    public void removeWishlisted() { wishlisted--;if(wishlisted<0)wishlisted=0; }
    public void addFavorited() { favorited++;}
    public void removeFavorited() { favorited--;if(favorited<0)favorited=0;}
    public void addRating(int rating) {
        switch(rating) {
        case 1: rated1++;break;
        case 2: rated2++;break;
        case 3: rated3++;break;
        case 4: rated4++;break;
        case 5: rated5++;break;
        }
    }
    
    public Long getBookId() { return id;}
    public int getRated1() { return rated1;}
    public int getRated2() { return rated2;}
    public int getRated3() { return rated3;}
    public int getRated4() { return rated4;}
    public int getRated5() { return rated5;}
    public int getReading() { return reading;}
    public int getWishlisted() { return wishlisted;}
    public int getFavorited() { return favorited;}
    public double getRating() {return rated;}
    
    public void setRated1(int r) { rated1 = r;}
    public void setRated2(int r) { rated2 = r;}
    public void setRated3(int r) { rated3 = r;}
    public void setRated4(int r) { rated4 = r;}
    public void setRated5(int r) { rated5 = r;}
    public void setReading(int r) { reading = r;}
    public void setWishlisted(int r) { wishlisted = r;}
    public void setFavorited(int r) {favorited = r;}
    
    @PrePersist
    public void updateRating() {
        calculateRating();
    }
}
