package com.pd.api.entity.aux;

import com.pd.api.security.AuthTools;

public class PasswordResetForm {

    public String token;
    
    public String username;
    
    private String password;
    
    public PasswordResetForm() {}
    public PasswordResetForm(String token, String username, String password) {
        this.token = token;
        this.username = username;
        this.password = AuthTools.encode(password);
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = AuthTools.encode(password);
    }
    
    public String getToken() {return token;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
}
