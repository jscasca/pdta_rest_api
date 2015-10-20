package com.pd.api.service.impl;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.entity.aux.PasswordResetForm;
import com.pd.api.exception.DuplicateResourceException;
import com.pd.api.exception.GeneralException;
import com.pd.api.security.AuthTools;

public class PublicServiceImplementation {

    protected PublicServiceImplementation() { }
    
    /**
     * 
     * @param user
     */
    public static void requestPasswordReset(String user) {
        AuthTools.processPasswordResetRequest(user);
    }
    
    /**
     * 
     * @param prf
     */
    public static void resetPassword(PasswordResetForm prf) {
        AuthTools.resetPassword(prf);
    }
    
    public static User registerMember(MemberRegistration registration) {
        //TODO Change for coherent exceptions
        //if(!com.pd.api.db.DAO.nameAvailable(registration.getUserName()))throw new DuplicateResourceException("User already exists");
        //TODO validate email too
        if(DAO.emailExists(registration.getEmail()))throw new DuplicateResourceException("Email is already in use");
        if(DAO.usernameExists(registration.getUserName()))throw new DuplicateResourceException("User already exists");
        
        User newUser = registration.getUserFromRegistrationForm();
        Credential credential = registration.getCredentialFromRegistrationForm(newUser);
        DAO.put(newUser);
        DAO.put(credential);
        if(newUser == null) throw new GeneralException("The user could not be created");
        return newUser;
    }
    
    /**
     * Return true is username is available
     * @param username
     * @return
     */
    public static boolean isUsernameAvailable(String username) {
        if(username == "") return false;
        return !DAO.usernameExists(username);
    }
    
    /**
     * Return true is email is available
     * @param email
     * @return
     */
    public static boolean isEmailAvailable(String email) {
        if(email == "")return false;
        return !DAO.emailExists(email);
    }
}
