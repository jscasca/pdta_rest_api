package com.pd.api.entity.aux;

import com.pd.api.entity.SocialProvider;

public class SocialLoginForm {
    
    public String id;
    
    public String token;
    
    public SocialLoginForm(){}
    public SocialLoginForm(String id, String token) {
        this.id = id;
        this.token = token;
    }
    
    public String getUserId() {
        return id;
    }
    
    public String getUserToken() {
        return token;
    }
}
