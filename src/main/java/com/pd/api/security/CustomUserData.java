package com.pd.api.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserData implements UserDetails {
    Collection<? extends GrantedAuthority> list = null;
    String userName = null;
    String password = null;
    boolean status = false;

    public CustomUserData() {
        list = new ArrayList<GrantedAuthority>();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.list;
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

    public String getUsername() {
        return this.userName;
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
