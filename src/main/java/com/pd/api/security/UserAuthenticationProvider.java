package com.pd.api.security;

import java.util.Arrays;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;

public class UserAuthenticationProvider implements AuthenticationProvider {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String username = authentication.getPrincipal() != null ? authentication.getPrincipal().toString() : null;
        String password = authentication.getCredentials() != null ? authentication.getCredentials().toString() : null;
        try {
            Credential credential = authenticate(username, password);
            final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password, SecurityRole.getCredentialRoles(credential));
            token.setDetails(credential);
            return token;
        } catch(Exception e) {
            throw new OAuth2Exception(e.getMessage(), e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
    
    public Credential authenticate(String username, String password) {
        Credential credential = DAO.getUniqueByUsername(Credential.class, username);
        if(credential == null) {
            throw new OAuth2Exception("The username is invalid");
        }
        if(!passwordEncoder.encode(password).equals(credential.getPassword())) {
            throw new OAuth2Exception("Not auhtenticable");
        }
        return credential;
    }

}
