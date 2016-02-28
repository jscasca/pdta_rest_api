package com.pd.api.service.impl;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

import com.google.common.collect.Sets;
import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.SocialProvider;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.entity.aux.PasswordResetForm;
import com.pd.api.entity.aux.SocialLoginForm;
import com.pd.api.entity.aux.SocialRegistration;
import com.pd.api.exception.DuplicateResourceException;
import com.pd.api.exception.GeneralException;
import com.pd.api.security.AuthTools;
import com.pd.api.security.AuthenticatedUserToken;
import com.pd.api.security.SecurityRole;
import com.pd.api.security.SocialLogin;

public class LoginServiceImplementation {

    protected LoginServiceImplementation() { }
    
    /**
     * 
     * @param user
     */
    public static void requestPasswordReset(String user) {
        AuthTools.processPasswordResetRequest(user);
    }
    
    public static boolean isUserRegistered(String provider, String userId) {
        SocialProvider socialProvider = DAO.getProviderByName(provider);
        SocialLogin socialLogin = DAO.getSocialLoginById(socialProvider, userId);
        return socialLogin != null;
    }
    
    public static AuthenticatedUserToken loginBySocialMedia(String provider, SocialLoginForm login) {
        AuthenticatedUserToken token = null;
        SocialProvider socialProvider = DAO.getProviderByName(provider);
        if(socialProvider == null) {throw new GeneralException("The provider is not valid");}
        SocialLogin socialLogin = DAO.getSocialLoginById(socialProvider, login.getUserId());
        if(socialLogin == null) {throw new GeneralException("The login is not registered yet.");}
        //TODO update the token and validate
        socialLogin.setToken(login.getUserToken());
        DAO.put(socialLogin);
        try {
            final Collection<SecurityRole> grantedAuths = SecurityRole.getSocialLoginRoles(socialLogin);
            final UserDetails principal = new org.springframework.security.core.userdetails.User(socialLogin.getUserId(), socialLogin.getToken(), grantedAuths);
            token = new AuthenticatedUserToken(principal, grantedAuths);
            token.setDetails(socialLogin);
        } finally {
            if(token == null) {
                throw new GeneralException("Something went wrong validating the token");
            }
        }
        return token;
    }
}
