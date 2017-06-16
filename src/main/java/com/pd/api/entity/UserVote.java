package com.pd.api.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name="user_vote")
public class UserVote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    //User
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
    
    //Posdta
    @ManyToOne
    @JoinColumn(name="posdta_id")
    private Posdta posdta;
    
    //Time and stuff
    @Type(type="timestamp")
    private Date creationDate = new Date();
    
    //Vote is boolean true = like; false = dislike
    boolean vote;
    
    protected UserVote(){}
    
    public UserVote(User u, Posdta p, boolean vote) {
        user = u;
        posdta = p;
        this.vote = vote;
    }
    
    public boolean isUpvote() {
        return vote == true;
    }
    
    public boolean isDownvote(){
        return vote == false;
    }

    public Posdta getPosdta() {
        return posdta;
    }
    
    public boolean getVote() {
        return vote;
    }
    
    public void setVote(boolean vote) {
        this.vote = vote;
    }
    
    public void makeUpvote() {
        this.vote = true;
    }
    
    public void makeDownvote() {
        this.vote = false;
    }
    
    public String toString() {
        return user.getUserName()+":"+posdta.getId()+(vote?"+1":"-1");
    }
}
