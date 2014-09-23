package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

import com.pd.api.db.DAO;

@Entity
@Table(name="role")
public class Role {

    protected static Role member = null, admin = null;
    
    protected static final String MEMBER = "member";
    protected static final String MEMBER_AUTH = "ROLE_USER";
    protected static final String ADMIN = "admin";
    protected static final String ADMIN_AUTH = "ROLE_ADMIN";
    
    static {
        
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String name;
    
    private String authority;
    
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
    
    public RoleAuthority getRoleAuthority() {
        return new RoleAuthority(authority);
    }
    
    public static Role getMember() {
        if(member != null) return member;
        DAO.put(member = new Role(MEMBER, MEMBER_AUTH));
        return member;
    }
    
    public static Role getAdmin() {
        if(admin != null) return admin;
        DAO.put(admin = new Role(ADMIN, ADMIN_AUTH));
        return admin;
    }
    
    /**
     * 
     * 
     * @author tin
     *
     */
    private class RoleAuthority implements GrantedAuthority {

        String role = null;
        
        public RoleAuthority(String authority) {
            role = authority;
        }
        
        @Override
        public String getAuthority() {
            return role;
        }
        
    }
}
