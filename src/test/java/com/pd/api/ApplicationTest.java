package com.pd.api;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;

import java.util.HashSet;
import java.util.Set;

public class ApplicationTest {

    public static void main(String[] args) {
        Credential c = DAO.getUniqueByUsername(Credential.class, "root");
        System.out.println(c);
        /*
        Credential c = DAO.get(Credential.class, 5L);
        Set<Role> roles = new HashSet<Role>();
        roles.add(DAO.getAdminRole());
        roles.add(DAO.getMemberRole());
        c.setRoles(roles);
        DAO.put(c);
        c.removeRoles(DAO.getMemberRole());
        DAO.put(c);
        /*
        User u = DAO.get(User.class, 1L);
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.getAdmin());
        roles.add(Role.getMember());
        Credential c = new Credential(u, "root", new HashSet<Role>());
        DAO.put(c);
        c.setRoles(roles);
        DAO.put(c);
        /*
        User u = new User("root","Oshima el gorila");
        Set<Role> roles = new HashSet<Role>();
        roles.add(Role.getAdmin());
        roles.add(Role.getMember());
        Credential c = new Credential(u, "root", roles);
        DAO.put(u);
        DAO.put(c);*/
    }
}
