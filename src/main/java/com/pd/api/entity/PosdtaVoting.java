package com.pd.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="posdta_voting")
public class PosdtaVoting {


    @Id
    @Column(name="posdta_id", unique=true, nullable=false)
    @GenericGenerator(name="gen", strategy="foreign", parameters=@Parameter(name="property", value="posdta"))
    @GeneratedValue(generator="gen")
    private Long id;
    
    @JsonIgnore
    @OneToOne(optional=false, fetch=FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private Posdta posdta;
    
    private int upvotes;
    private int downvotes;
    
    @Transient
    private String className = "PosdtaVote";
    
    public PosdtaVoting() {
        upvotes = 0;
        downvotes = 0;
    }
    
    public void setPosdta(Posdta posdta) {
        this.posdta = posdta;
    }
    
    public int getUpvotes() {
        return upvotes;
    }
    
    public int getDownvotes() {
        return downvotes;
    }
    
    public void upvote() {
        upvotes++;
    }
    
    public void downvote() {
        downvotes++;
    }
    
    public void removeUpvote() {
        upvotes--;
    }
    
    public void removeDownvote() {
        downvotes--;
    }
    
    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }
    
    public void setDownvotes(int downvotes) {
        this.downvotes = downvotes;
    }
    
    public void changeToUpvote() {
        downvotes--;
        upvotes++;
    }
    
    public void changeToDownvote() {
        downvotes++;
        upvotes--;
    }
    
    public String toString() {
        return upvotes+"-"+downvotes;
    }
}
