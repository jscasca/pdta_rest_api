package com.pd.api.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="posdta_voting")
public class PosdtaVoting {

    @Id
    @Column(name="posdta_id")
    private Long id;
    
    private int upvotes;
    private int downvotes;
    
    protected PosdtaVoting() {
        upvotes = 0;
        downvotes = 0;
    }
    public PosdtaVoting(Posdta p) {
        super();
        id = p.getId();
    }
    
    public PosdtaVoting(Long posdtaId) {
        super();
        id = posdtaId;
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
}
