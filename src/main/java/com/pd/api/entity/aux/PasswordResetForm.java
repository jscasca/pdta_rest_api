package com.pd.api.entity.aux;

import com.pd.api.security.AuthTools;

public class PasswordResetForm {

    public String token;
    
    private String password;
    
    public PasswordResetForm() {}
    public PasswordResetForm(String token, String password) {
        this.token = token;
        this.password = AuthTools.encode(password);
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public void setPassword(String password) {
        this.password = AuthTools.encode(password);
    }
    
    public String getToken() {return token;}
    public String getPassword() {return password;}
}
