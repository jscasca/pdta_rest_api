package com.pd.api.service.impl;

import com.pd.api.db.DAO;
import com.pd.api.entity.User;
import com.pd.api.exception.GeneralException;

public class MyServiceImplementation {

    public static User getMe(String username) {
        User user = DAO.getUserByUsername(username);
        if(user == null) throw new GeneralException("","");
        return user;
    }
}
