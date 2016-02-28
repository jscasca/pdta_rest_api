package com.pd.api.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;

public class UserAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    public UserAuthenticationProvider() {
        this(new BCryptPasswordEncoder());
    }
    
    public UserAuthenticationProvider(PasswordEncoder passwordEncoder) {
        super();
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getPrincipal() != null ? authentication.getPrincipal().toString() : null;
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        try {
            final Collection<SecurityRole> grantedAuths;
            Object tokenDetails;
            if(SocialLogin.isSocialCredential(username)) {
                //Authenticate by social
                SocialLogin login = AuthTools.authenticateSocialLogin(username, password);
                grantedAuths = SecurityRole.getSocialLoginRoles(login);
                tokenDetails = login;
            } else {
                //Authenticate by cred
                Credential credential = AuthTools.authenticate(username, password);
                grantedAuths = SecurityRole.getCredentialRoles(credential);
                tokenDetails = credential;
            }
            final UserDetails principal = new User(username, password, grantedAuths);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
            token.setDetails(tokenDetails);
            return token;
        } catch(Exception e) {
            throw new OAuth2Exception(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
