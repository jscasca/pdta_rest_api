package com.pd.api.entity.aux;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import com.pd.api.entity.Credential;
import com.pd.api.entity.User;

public class MemberRegistration {

    public String username;
    
    public String displayname;
    
    public String password;
    
    public MemberRegistration(){}
    public MemberRegistration(String n, String d, String p) {
        this.username = n;
        this.displayname = d;
        this.password = p;
    }
    
    public void setUserName(String n) {
        username = n;
    }
    
    public void setDisplayName(String d) {
        displayname = d;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String getUserName() {
        return username;
    }
    
    public String getDisplayName() {
        return displayname;
    }
    
    public String getPassword() {
        return password;
    }
    
    public User getUserFromRegistrationForm() {
        return new User(username, displayname);
    }
    
    public Credential getCredentialFromRegistrationForm(User user) {
        return new Credential(user, password);
    }
    
    public static String generatePassword(String passphrase) throws NoSuchAlgorithmException, InvalidKeySpecException {
        int iterations = 1000;
        char[] chars = passphrase.toCharArray();
        byte[] salt = getSalt().getBytes();
        
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return iterations + ":" + toHex(salt) + toHex(hash);
    }
    
    private static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt.toString();
    }
    
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
