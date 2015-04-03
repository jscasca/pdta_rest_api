package com.pd.api.service;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pd.api.security.CustomUserData;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute
    public CustomUserData customPrincipal(Principal principal) {
        if(principal == null) return null;
        if(principal instanceof OAuth2Authentication) {
            OAuth2Authentication auth = (OAuth2Authentication)principal;
            Object principalObj = auth.getPrincipal();
            if(principalObj instanceof User) {
                User user = (User)principalObj;
                return new CustomUserData(user);
            } else {
                String principalString = (String)principalObj;
            }
        } else {
            //if principal instance of UsernamePasswordAuthenticationToken
            String username = principal.getName();
        }
        return null;
    }
}
