package com.pd.api.service.impl;

import java.util.List;
import java.util.Set;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.LoggedInWrapper;
import com.pd.api.exception.GeneralException;

public class MyServiceImplementation {

    public static User getMe(String username) {
        User user = DAO.getUserByUsername(username);
        if(user == null) throw new GeneralException("User is null");
        return user;
    }
    
    public static LoggedInWrapper logIn(String username) {
        Credential credential = DAO.getUniqueByUsername(Credential.class, username);
        return new LoggedInWrapper(credential);
    }
}
