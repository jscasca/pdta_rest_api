package com.pd.api.service;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.pd.api.security.CustomUserData;

@ControllerAdvice
public class SecurityControllerAdvice {

    @ModelAttribute
    public CustomUserData customPrincipal(Authentication auth) {
        if(auth == null) return null;
        return (CustomUserData)auth.getPrincipal();
    }
}
