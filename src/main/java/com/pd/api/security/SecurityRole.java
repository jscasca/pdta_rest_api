package com.pd.api.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;

/**
 * SecurityRoles hold granted authorities for authentication
 * @author tin
 *
 */
public class SecurityRole implements GrantedAuthority {

    /**
     * Default UID
     */
    private static final long serialVersionUID = 1L;
    
    String role = null;
    
    public SecurityRole(String authority) {
        this.role = authority;
    }
    @Override
    public String getAuthority() {
        return role;
    }
    
    public void setAuthority(String authority) {
        this.role = authority;
    }
    
    public static Collection<SecurityRole> getCredentialRoles(Credential credential) {
        Collection<SecurityRole> roles = new ArrayList<SecurityRole>();
        for(Role role : credential.getRoles()) {
            roles.add(new SecurityRole(role.getAuthority()));
        }
        return roles;
    }

}
