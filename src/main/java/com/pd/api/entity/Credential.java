package com.pd.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.collect.Sets;
import com.pd.api.db.DAO;
import com.pd.api.security.AuthTools;

/**
 * 
 * @author tin
 *
 */
@Entity
@Table(name="credential", 
    uniqueConstraints = { 
        @UniqueConstraint(columnNames = { "username" }), 
        @UniqueConstraint(columnNames = { "email" })})
public class Credential implements Serializable {


    public static final Pattern usernameValidator = Pattern.compile("^[a-zA-Z0-9_]{1,24}$");
    public static final Pattern emailValidator = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    
    private String username;
    
    private String email;
    
    private String password;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "credential_role", 
        joinColumns = {@JoinColumn(name = "credential_id")}, 
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
    
    @OneToOne(cascade = {CascadeType.ALL})
    private User user;
    
    public Credential(){}
    public Credential(User user, String email, String password) { this(user, email, password, DAO.getMemberRole());}
    public Credential(User user, String email, String password, Role role) {this(user, email, password, Sets.newHashSet(role));}
    public Credential(User user, String email, String password, Set<Role> roles) {
        this.user = user;
        this.username = this.user.getUserName();
        this.email = email;
        this.password = password;
        this.roles = new HashSet<Role>(roles);
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    /**
     * Returns a set of roles for the credential.
     * The returned set is a defensive copy
     * 
     * @return a set with roles
     */
    public Set<Role> getRoles() {
        //Defensive copy, nobody will be able to change the list from the outside
        return new HashSet<Role>(roles);
    }
    
    public User getUser() {
        return user;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    @Override
    public String toString() {
        return username + ":" + id + " [" + user + "]";
    }
    
    @PrePersist
    public void onPrePersist() {
        if(!isValidEmail(email)) {
            throw new IllegalArgumentException("The email address does not comply with an email address valid syntax");
        }
        if(!isValidUsername(username)) {
            throw new IllegalArgumentException("The username does not comply with username valid syntax");
        }
    }
    
    public static boolean isValidEmail(String email) {
        Matcher m = emailValidator.matcher(email);
        return m.matches();
    }
    
    public static boolean isValidUsername(String username) {
        Matcher m = usernameValidator.matcher(username);
        return m.matches();
    }
}
