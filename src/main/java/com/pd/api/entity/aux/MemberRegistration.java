package com.pd.api.entity.aux;

import com.pd.api.entity.Credential;
import com.pd.api.entity.User;
import com.pd.api.security.AuthTools;

public class MemberRegistration {

    public String username;
    
    public String email;
    
    public String password;
    
    public MemberRegistration(){}
    public MemberRegistration(String n, String e, String p) {
        this.username = n;
        this.email = e;
        this.password = p;
    }
    
    public void setUserName(String n) {
        username = n;
    }
    
    public void setEmail(String e) {
        email = e;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String getUserName() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public User getUserFromRegistrationForm() {
        return new User(username);
    }
    
    public Credential getCredentialFromRegistrationForm(User user) {
        return AuthTools.registerCredential(user, email, password);
    }
}
