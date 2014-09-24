package com.pd.api.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;

public class CustomUserDetailsService implements UserDetailsService {

    public UserDetails loadUserByUsername(String authentication) throws UsernameNotFoundException {
        CustomUserData customUserData = new CustomUserData();
        // You can talk to any of your user details service and get the
        // authentication data and return as CustomUserData object then spring
        // framework will take care of the authentication
        Credential credential = DAO.getUniqueByUsername(Credential.class, authentication);
        if(credential == null) return null;
        customUserData.setAuthentication(true);
        customUserData.setUsername(authentication);
        customUserData.setPassword(credential.getPassword());
        customUserData.setAuthorities(getCredentialRoles(credential));
        return customUserData;
    }
    
    private Collection<CustomRole> getCredentialRoles(Credential credential) {
        Collection<CustomRole> roles = new ArrayList<CustomRole>();
        for(Role role : credential.getRoles()) {
            roles.add(new CustomRole(role.getAuthority()));
        }
        return roles;
    }

    /**
     * Custom Role class to manage the authorities
     * 
     * @author tin
     *
     */
    private class CustomRole implements GrantedAuthority {
        String role = null;
        
        public CustomRole(String roleName) {
            this.role = roleName;
        }

        public String getAuthority() {
            return role;
        }

        public void setAuthority(String roleName) {
            this.role = roleName;
        }

    }

}
