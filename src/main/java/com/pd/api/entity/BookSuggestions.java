package com.pd.api.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Basic;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.pd.api.db.DAO;
import com.pd.api.util.DataPair;
import com.pd.api.util.RandomCollection;
import com.pd.api.util.StringUtil;

@Entity
@Table(name="book_suggestions", uniqueConstraints = @UniqueConstraint(columnNames = { "user_id" }))
public class BookSuggestions implements Serializable{

    @Id
    private Long id;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
    
    //@ElementCollection
    //private NavigableMap<Double, DataPair> map = new TreeMap<Double, DataPair>();
    
    //private RandomCollection recommendations;
    
    @ElementCollection
    @Basic
    @Lob
    private String map;
    
    public BookSuggestions() {}
    public BookSuggestions(User user) {
        this.id = user.getId();
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
    
    public List<Long> getRandomIds(int size) {
        //TODO implement
        return null;
    }
    
    public List<Long> browseIds(int start, int limit) {
        //TODO: implement
        return null;
    }
    
    public String getMap() {
        return map;
    }
    
    public String getMapAsSortedMap() {
        return null;
    }
    
    public NavigableMap<Double, Long> getMapAsNavigableMap() {
        return null;
    }
    
    public void updateMap(HashMap<Long, Double> suggestions) {
        updateMap(StringUtil.valueMapToString(suggestions));
    }
    
    private void updateMap(String suggestions) {
        this.map = suggestions;
        //select book_id, count(*) from posdta where user_id in (select user_id from posdta where book_id in (select book_id from posdta where user_id = '7') and user_id <> '7') and book_id not in (select book_id from posdta where user_id = '7') group by book_id;
    }
    
    /*public void storeRecommendations() {
        if(recommendations.size()>0) {
            recommendationString = StringUtil.valueMapToString(recommendations);
        } else {
            recommendationString = "";
        }
    }
    
    public void loadRecommendationsMap() {
        if(recommendationString != null && recommendationString != "") {
            recommendations = StringUtil.stringToValueMap(recommendationString);
        }
    }*/
    /*
    public List<DataPair> getRecommendations(int n) {
        return recommendations.nextN(n);
    }
    
    public void getUserRecommendations() {
        
        
        //TODO: implement for real
    }
    */
    /*
    
     */
}
