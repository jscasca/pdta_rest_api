package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="work_rating", uniqueConstraints = @UniqueConstraint(columnNames = { "work_id" }))
public class WorkRating {

    @Id
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="work_id")
    private Work work;
    
    private int countR1 = 0;
    private int countR2 = 0;
    private int countR3 = 0;
    private int countR4 = 0;
    private int countR5 = 0;
    
    public WorkRating() {}
    public WorkRating(Work work) {
        this.work = work;
        this.id = work.getId();
    }
    
    public int getRating1Count() {
        return countR1;
    }
    
    public int getRating2Count() {
        return countR2;
    }
    
    public int getRating3Count() {
        return countR3;
    }
    
    public int getRating4Count() {
        return countR4;
    }
    
    public int getRating5Count() {
        return countR5;
    }
    
    public int getRatingCount(int rating) {
        int count = 0;
        switch(rating) {
            case 1: count = countR1; break;
            case 2: count = countR2; break;
            case 3: count = countR3; break;
            case 4: count = countR4; break;
            case 5: count = countR5; break;
        }
        return count;
    }
    
    public void updateCounter(int rating) {
        switch(rating) {
            case 1: countR1++; break;
            case 2: countR2++; break;
            case 3: countR3++; break;
            case 4: countR4++; break;
            case 5: countR5++; break;
        }
    }
    
    public double getAverage() {
        int voters = countR1 + countR2 + countR3 + countR4 + countR5;
        int points = (countR1 * 1) + (countR2 * 2) + (countR3 * 3) + (countR4 * 4) + (countR5 * 5);
        return ((double)points) / voters;
    }
}
