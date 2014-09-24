package com.pd.api.service.impl;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.exception.GeneralException;

public class PublicServiceImplementation {

    protected PublicServiceImplementation() { }
    
    public static User registerMember(MemberRegistration registration) {
        //TODO Change for coherent exceptions
        if(!com.pd.api.db.DAO.nameAvailable(registration.getUserName()))throw new GeneralException("Duplicate User","duplicate");
        User newUser = registration.getUserFromRegistrationForm();
        Credential credential = registration.getCredentialFromRegistrationForm(newUser);
        DAO.put(newUser);
        DAO.put(credential);
        return newUser;
        
        /*
         * if(!DAO.nameAvailable(member.getName()))throw new DuplicateResourceException("User","");
        User newUser = member.getMemberUser();
        try {
            Credential credential = member.getCredential(newUser);
            DAO.put(newUser);
            DAO.put(credential);
            return newUser;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage(), "");
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            throw new GeneralException(e.getMessage(), "");
        }
         */
    }
}
