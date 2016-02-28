package com.pd.api.entity.aux;

import java.util.Set;

import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.SocialProvider;
import com.pd.api.entity.User;
import com.pd.api.security.AuthTools;
import com.pd.api.security.SocialLogin;

public class SocialRegistration {

    public static final String FACEBOOK = "facebook";
    
    public String id;
    
    public String token;
    
    public String username;
    
    public String icon;
    
    public SocialRegistration(){}
    public SocialRegistration(String id, String token, String username, String icon) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.icon = icon;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getUserId() {
        return id;
    }
    
    public String getUserToken() {
        return token;
    }
    
    public String getIcon() {
        return icon;
    }
    
    //TODO make a rule for each provider instead of hardcoding it
    public User getUserFromRegistrationForm() {
        return new User(username, username, icon);
    }
    
    public SocialLogin getLoginFromRegistrationForm(User user, SocialProvider provider, Set<Role> roles) {
        return AuthTools.registerSocial(user, provider, id, token, roles);
    }
}
