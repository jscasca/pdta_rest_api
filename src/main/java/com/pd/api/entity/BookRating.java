package com.pd.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="book_rating", uniqueConstraints = @UniqueConstraint(columnNames = { "book_id" }))
public class BookRating {
    
    /*
     * 
    @Id
    @Column(name="employee_id", unique=true, nullable=false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="employee"))
    private Long employeeId;
    
     *
     *@GenericGenerator(name = "generator", strategy = "foreign", 
    parameters = @Parameter(name = "property", value = "stock"))
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "STOCK_ID", unique = true, nullable = false)
     */
    @Id
    @Column(name="book_id", unique=true, nullable=false)
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="book"))
    @GeneratedValue(generator="gen")
    private Long id;
    
    @JsonIgnore
    @OneToOne(optional=false, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Book book;
    
    private int rated1;
    private int rated2;
    private int rated3;
    private int rated4;
    private int rated5;
    private double rated = 0;
    private int reading;
    private int wishlisted;
    private int favorited;
    
    public BookRating() {this(0, 0, 0, 0, 0, 0, 0, 0);}
    public BookRating(int r1, int r2, int r3, int r4, int r5, int ring, int wish, int fav) {
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
        calculateRating();
    }
    
    public Long getId() { return id;}
    public int getRated1() { return rated1;}
    public int getRated2() { return rated2;}
    public int getRated3() { return rated3;}
    public int getRated4() { return rated4;}
    public int getRated5() { return rated5;}
    public int getReading() { return reading;}
    public int getWishlisted() { return wishlisted;}
    public int getFavorited() { return favorited;}
    public double getRating() {return rated;}
    
    public void setBook(Book book){this.book = book;}
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
