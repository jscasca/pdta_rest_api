package com.pd.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User implements Serializable {
    
    public static final String default_icon = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String username;
    
    private String displayname;
    
    private String icon;
    
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name = "followers",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "follower_id")})
    private Set<User> following;
    
    //@ManyToMany(mappedBy = "following")
    //private Set<User> followers;
    
    public User(){}
    public User(String userName, String displayName) { this(userName, displayName, User.default_icon);}
    public User(String userName, String displayName, String icon) {
        this.username = userName;
        this.displayname = displayName;
        this.icon = icon;
        this.following = new HashSet<User>();
    }
    
    public Long getId() {
        return id;
    }
    
    public String getUserName() {
        return username;
    }
    
    public String getDisplayName() {
        return displayname;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public void setDisplayName(String displayName) {
        this.displayname = displayName;
    }
    
    public void setIcon(String icon) {
        this.icon = icon;
    }
    
    public void setFollowees(Set<User> followees) {
        this.following = new HashSet<User>(followees);
    }
    
    public void addFollowee(User follower) {
        following.add(follower);
    }
    
    public void removeFollowee(User follower) {
        following.remove(follower);
    }
    
    @Override
    public String toString() {
        return username + ":" + id;
    }
}