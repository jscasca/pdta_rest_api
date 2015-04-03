package com.pd.api.service;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import com.pd.api.security.CustomUserData;

public abstract class AbstractService {

    protected CustomUserData loadUserFromSecurityContext() {
        OAuth2Authentication auth = (OAuth2Authentication) SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        User user = null;
        String classType = principal.getClass().toString();
        if(principal instanceof User) {
            user = (User)principal;
            return new CustomUserData(user);
        } else {
            String userName = (String)principal;
        }
        return null;
    }
}
