package com.pd.api;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.MemberRegistration;

import java.util.HashSet;
import java.util.Set;

public class ApplicationTest {

    public static void main(String[] args) {
        
        /*
        User user = DAO.getUserByUsername("test2");
        User following = DAO.getUserById(1L);
        user.addFollowee(following);
        DAO.put(user);
        System.out.println(user);*/
        
        /*
         * public static User followUser(String username, Long id) {
        User user = DAO.getUserByUsername(username);
        User following = DAO.getUserById(id);
        user.addFollower(following);
        DAO.put(user);
        return following;
    }
         */
    }
}
