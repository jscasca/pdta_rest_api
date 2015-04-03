package com.pd.api.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserData implements UserDetails {
    Collection<? extends GrantedAuthority> list = null;
    String username = null;
    String password = null;
    boolean status = false;
    
    public CustomUserData(User user) {
        this(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public CustomUserData(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        //super(username, password, authorities);
        this.username = username;
        this.password = password;
        list = authorities;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        return (Collection<GrantedAuthority>) this.list;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> roles) {
        this.list = roles;
    }

    public void setAuthentication(boolean status) {
        this.status = status;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }
    
    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

}
