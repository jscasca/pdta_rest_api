package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="user", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class User {
    
    public static final String default_icon = "";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String username;
    
    private String displayname;
    
    private String icon;
    
    public User(){}
    public User(String userName, String displayName) { this(userName, displayName, User.default_icon);}
    public User(String userName, String displayName, String icon) {
        this.username = userName;
        this.displayname = displayName;
        this.icon = icon;
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
}
