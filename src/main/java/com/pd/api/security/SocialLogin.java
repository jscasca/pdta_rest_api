package com.pd.api.security;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.collect.Sets;
import com.pd.api.entity.Role;
import com.pd.api.entity.SocialProvider;
import com.pd.api.entity.User;

@Entity
@Table(name="social_login", 
    uniqueConstraints = { 
        @UniqueConstraint(columnNames = { "social_provider_id" }), 
        @UniqueConstraint(columnNames = { "providerUserId" })})
public class SocialLogin {
    
    public static final Pattern providerValidator = Pattern.compile("^[a-z0-9]+:[a-zA-Z0-9]+$");

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    @ManyToOne
    @JoinColumn(name="social_provider_id")
    private SocialProvider socialProvider;
    
    private String providerUserId;
    private String accessToken;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "social_role", 
        joinColumns = {@JoinColumn(name = "social_id")}, 
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
    
    @OneToOne
    private User user;
    
    public SocialLogin() {}
    public SocialLogin(User user, SocialProvider provider, String userId, String token, Role role) { this(user, provider, userId, token, Sets.newHashSet(role));}
    public SocialLogin(User user, SocialProvider provider, String userId, String token, Set<Role> roles) {
        this.user = user;
        this.socialProvider = provider;
        this.providerUserId = userId;
        this.accessToken = token;
        this.roles = new HashSet<Role>(roles);
    }
    
    public SocialProvider getProvider() {
        return socialProvider;
    }
    
    public User getUser() {
        return user;
    }
    
    public String getUserId() {
        return providerUserId;
    }
    
    public String getToken() {
        return accessToken;
    }
    
    public void setToken(String newToken) {
        this.accessToken = newToken;
    }
    
    public Set<Role> getRoles() {
        //Defensive copy, nobody will be able to change the list from the outside
        return new HashSet<Role>(roles);
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = new HashSet<Role>(roles);
    }
    
    public void addRole(Role role) {
        this.roles.add(role);
    }
    
    public void removeRoles(Role role) {
        this.roles.remove(role);
    }
    
    public String toString() {
        return socialProvider.getProviderName()+":"+providerUserId;
    }
    
    public static boolean isSocialCredential(String authentication) {
        //The auth must be of the type provider:id
        Matcher m = providerValidator.matcher(authentication);
        return m.matches();
    }
    
}
