package com.pd.api.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.pd.api.db.DAO;

@Entity
@Table(name="role")
public class Role implements Serializable {

    protected static Role member = null, admin = null;
    
    public static final String MEMBER = "member";
    protected static final String MEMBER_AUTH = "ROLE_USER";
    public static final String ADMIN = "admin";
    protected static final String ADMIN_AUTH = "ROLE_ADMIN";
    
    static {
        member = getMember();
        admin = getAdmin();
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String name;
    
    private String authority;
    
    @ManyToMany(mappedBy="roles")
    private Set<Credential> credentials = new HashSet<Credential>();
    
    public Role(){}
    public Role(String name, String authority) {
        this.name = name;
        this.authority = authority;
    }
    
    public String getName() {
        return name;
    }
    
    public String getAuthority() {
        return authority;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAuthority(String authority) {
        this.authority = authority;
    }
    
    public static Role getMember() {
        if(member != null) return member;
        if( (member = DAO.getMemberRole()) == null) {
            DAO.put(member = new Role(MEMBER, MEMBER_AUTH));
        }
        return member;
    }
    
    public static Role getAdmin() {
        if(admin != null) return admin;
        if( (admin = DAO.getAdminRole()) == null) {
            DAO.put(admin = new Role(ADMIN, ADMIN_AUTH));
        }
        return admin;
    }
    
    @Override
    public String toString() {
        return name + ":" + id;
    }
}
