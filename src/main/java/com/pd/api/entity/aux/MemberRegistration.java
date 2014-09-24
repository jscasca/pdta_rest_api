package com.pd.api.entity.aux;

import com.pd.api.entity.Credential;
import com.pd.api.entity.User;

public class MemberRegistration {

    private String username;
    
    private String displayname;
    
    private String password;
    
    public MemberRegistration(){}
    public MemberRegistration(String n, String d, String p) {
        this.username = n;
        this.displayname = d;
        this.password = p;
    }
    
    public void setUserName(String n) {
        username = n;
    }
    
    public void setDisplayName(String d) {
        displayname = d;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String getUserName() {
        return username;
    }
    
    public String getDisplayName() {
        return displayname;
    }
    
    public String getPassword() {
        return password;
    }
    
    public User getUserFromRegistrationForm() {
        return new User(username, displayname);
    }
    
    public Credential getCredentialFromRegistrationForm(User user) {
        return new Credential(user, password);
    }
}
