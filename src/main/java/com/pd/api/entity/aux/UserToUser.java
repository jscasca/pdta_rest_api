package com.pd.api.entity.aux;

import com.pd.api.entity.User;

public class UserToUser {
    
    private boolean following;
    private boolean follower;

    private User owner;
    private User viewed;
    
    public UserToUser() {}
    public UserToUser(User owner, User viewed) {
        this.owner = owner;
        this.viewed = viewed;
    }
    
    public boolean isFollowing() {
        return following;
    }
     
    public boolean isFollower() {
        return follower;
    }
}
