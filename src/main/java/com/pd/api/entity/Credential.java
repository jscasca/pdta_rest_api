package com.pd.api.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.collect.Sets;

/**
 * 
 * @author tin
 *
 */
@Entity
@Table(name="credential", uniqueConstraints = @UniqueConstraint(columnNames = { "username" }))
public class Credential implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String username;
    
    private String password;
    
    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "credential_role", 
        joinColumns = {@JoinColumn(name = "credential_id")}, 
        inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;
    
    @OneToOne
    private User user;
    
    public Credential(User user, String password) { this(user,password, Role.member);}
    public Credential(User user, String password, Role role) {this(user, password, Sets.newHashSet(role));}
    public Credential(User user, String password, Set<Role> roles) {
        this.user = user;
        this.username = this.user.getUserName();
        this.password = password;
        this.roles = roles;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public Set<Role> getRoles() {
        return roles;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    public void addRole(Role role) {
        this.roles.add(role);
    }
    
    public void removeRoles(Role role) {
        this.roles.remove(role);
    }
}
