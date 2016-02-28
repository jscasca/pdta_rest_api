package com.pd.api.service.impl;

import java.util.Set;

import com.google.common.collect.Sets;
import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.SocialProvider;
import com.pd.api.entity.User;
import com.pd.api.entity.aux.MemberRegistration;
import com.pd.api.entity.aux.PasswordResetForm;
import com.pd.api.entity.aux.SocialRegistration;
import com.pd.api.exception.DuplicateResourceException;
import com.pd.api.exception.GeneralException;
import com.pd.api.security.AuthTools;
import com.pd.api.security.AuthenticatedUserToken;
import com.pd.api.security.SocialLogin;

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
    
    public static User registerMemberSocialMedia(SocialRegistration registration, String providerId) {
        //Check if name is available
        if(DAO.usernameExists(registration.getUsername()))throw new DuplicateResourceException("User already exists");
        //Check if the social login has not been used
        SocialProvider provider = DAO.getProviderByName(providerId);
        if(provider == null) {
            throw new GeneralException("The provider is not supported yet");
        }
        
        SocialLogin login = DAO.getSocialLoginById(provider, registration.getUserId());
        if(login != null) {
            throw new DuplicateResourceException("Login already exists for that user");
        }
        
        //TODO check if the token is valid by calling the social api
        User newUser = registration.getUserFromRegistrationForm();
        //TODO: some weird shit to select roles from here
        Set<Role> roles = Sets.newHashSet(DAO.getMemberRole());
        login = registration.getLoginFromRegistrationForm(newUser, provider, roles);
        newUser = DAO.put(newUser);
        if(newUser == null) throw new GeneralException("The user could not be created");
        DAO.put(login);
        return newUser;
    }
    
    public static User registerMember(MemberRegistration registration) {
        //TODO Change for coherent exceptions
        //if(!com.pd.api.db.DAO.nameAvailable(registration.getUserName()))throw new DuplicateResourceException("User already exists");
        //TODO validate email too
        if(DAO.emailExists(registration.getEmail()))throw new DuplicateResourceException("Email is already in use");
        if(DAO.usernameExists(registration.getUserName()))throw new DuplicateResourceException("User already exists");
        
        User newUser = registration.getUserFromRegistrationForm();
        Credential credential = registration.getCredentialFromRegistrationForm(newUser);
        newUser = DAO.put(newUser);
        if(newUser == null) throw new GeneralException("The user could not be created");
        DAO.put(credential);
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
