package com.pd.api.security;

import java.util.Set;

import com.pd.api.http.HttpConn;
import com.pd.api.mail.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.pd.api.db.DAO;
import com.pd.api.entity.Credential;
import com.pd.api.entity.Role;
import com.pd.api.entity.SocialProvider;
import com.pd.api.entity.User;
import com.pd.api.entity.VerificationToken;
import com.pd.api.entity.VerificationToken.VerificationType;
import com.pd.api.entity.aux.PasswordResetForm;
import com.pd.api.exception.InvalidAuthenticationException;

public class AuthTools {

    private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    //Put here the email implementation
    
    public static Credential authenticate(String authentication, String password) {
        //Authentication can be an email or a username
        //TODO try out new function
        Credential credential = null;
        if(Credential.isValidUsername(authentication)) {
            credential = DAO.getUniqueByUsername(Credential.class, authentication);
        } else if(Credential.isValidEmail(authentication)){
            credential = DAO.getUnique(Credential.class, "where email = ?", authentication);
        }
        if(credential == null) {
            throw new InvalidAuthenticationException("The username is invalid or does not exist");
        }
        if(!passwordEncoder.matches(password, credential.getPassword())) {
            throw new InvalidAuthenticationException("Not authenticable");
        }
        return credential;
    }
    
    public static SocialLogin authenticateSocialLogin(String authentication, String token) {
        String[] a = authentication.split(":");
        String providerString = a[0];
        String user = a[1];
        SocialProvider provider = DAO.getProviderByName(providerString);
        SocialLogin login = DAO.getSocialLoginById(provider, user);
        return login;
    }
    
    private static Credential getCredentialFromAuthentication(String authentication) {
        Credential credential = null;
        if(Credential.isValidUsername(authentication)) {
            credential = DAO.getUniqueByUsername(Credential.class, authentication);
        } else if(Credential.isValidEmail(authentication)){
            credential = DAO.getUnique(Credential.class, "where email = ?", authentication);
        }
        return credential;
    }
    
    public static void processPasswordResetRequest(String user) {
        Credential credential = getCredentialFromAuthentication(user);
        if(credential != null) {
            VerificationToken token = new VerificationToken(credential, VerificationType.PASSWORD_RESET, VerificationToken.DEFAULT_PASSWORD_RESET);
            token = DAO.put(token);
            // TODO: Make a mailing factory
            Mailer mailer = new RemoteSibMailer(); // Maybe this should come from a factory?
            try {
                mailer.sendVerificationMail(token);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void resetPassword(PasswordResetForm prf) {
        VerificationToken token = DAO.getVerificationToken(prf.getToken());
        if(token == null) { throw new InvalidAuthenticationException("Invalid or expired token");}
        if(token.hasExpired()) {throw new InvalidAuthenticationException("Invalid or expired token");}
        Credential credential = DAO.getCredentialByEmail(token.getEmail());
        if(credential == null) { throw new InvalidAuthenticationException("Invalid username");}
        //if(!credential.getEmail().equals(token.getEmail())) { throw new InvalidAuthenticationException("This token is not yours");}
        credential.setPassword(prf.getPassword());
        DAO.put(credential);
        DAO.delete(token);
        //And rmeove the used token
    }
    
    public static Credential registerCredential(User user, String email, String password) {
        return new Credential(user, email, passwordEncoder.encode(password));
    }
    
    public static SocialLogin registerSocial(User user, SocialProvider provider, String token, String id, Set<Role> roles) {
        return new SocialLogin(user, provider, id, token, roles);
    }
    
    public static String encode(String plain) {
        return passwordEncoder.encode(plain);
    }
    
    public static void main(String args[]) {
        String test = passwordEncoder.encode("test");
        String otherTest = passwordEncoder.encode("test");
        if(passwordEncoder.matches("test", test)) {
            System.out.println("MATCH");
        }
        if(test.equals(otherTest)) {
            System.out.println("Same");
        }
    }
}
