package com.pd.api.security;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthenticatedUserToken extends AbstractAuthenticationToken {

    /**
     * 
     */
    private static final long serialVersionUID = -6092946284153352007L;
    
    private final Object principal;
    private Object credentials;
    
    public AuthenticatedUserToken(Object principal,
            Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

}
