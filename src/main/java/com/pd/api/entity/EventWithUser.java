package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="event_user")
@PrimaryKeyJoinColumn(name="id")
public class EventWithUser extends Event{

    @ManyToOne
    @JoinColumn(name="target_id")
    private User target;
    
    @Transient
    private String className = "User Event";
    
    protected EventWithUser() {}
    
    public EventWithUser(User user, EventType type, User target) {
        super(user, type);
        this.target = target;
    }
    
    public User getTarget() {
        return target;
    }
    
    public void setTarget(User target) {
        this.target = target;
    }
}
