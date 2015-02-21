package com.pd.api.entity.aux;

import java.util.HashSet;
import java.util.Set;

import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;

public class LoggedInWrapper {

    private User user;
    
    private Set<String> roles;
    
    public LoggedInWrapper() {}
    public LoggedInWrapper(Credential credential) {
        this.user = credential.getUser();
        roles = new HashSet<String>();
        for(Role role : credential.getRoles()) {
            roles.add(role.getName());
        }
    }
    
    public User getUser() {
        return this.user;
    }
    
    public Set<String> getRoles() {
        return this.roles;
    }
}
