package com.pd.api.entity;

import java.util.Date;
import java.util.HashMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user_recommendations", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id" }))
public class UserRecommendations {

    @Id
    @Column(name="user_id")
    private Long id;
    
    @javax.persistence.MapKey()
    private HashMap<Long, Double> recommendations;
    
    @Type(type="timestamp")
    private Date last = new Date();
    
    public UserRecommendations(){}
    public UserRecommendations(User u) {}
    public UserRecommendations(User u, HashMap<Long, Double> recommendations) {
        this.id = u.getId();
        this.recommendations = recommendations;
    }
    
    public HashMap<Long, Double> getRecommendations() {
        return recommendations;
    }
    
    public Long getId() {
        return id;
    }
    
    public Date getLastTime() {
        return last;
    }
    
    public void setRecommendations(HashMap<Long, Double> newRecommendations) {
        recommendations = newRecommendations;
        last = new Date();
    }
    
    public String toString() {
        return id +":"+last.toString();
    }
    
    //TODO: onprepersists set last date?
}
