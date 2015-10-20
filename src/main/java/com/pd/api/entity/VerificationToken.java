package com.pd.api.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;


@Entity(name="verification_token")
public class VerificationToken {

    public enum VerificationType { 
        PASSWORD_RESET,
        EMAIL_VERIFICATION
    };
    
    private static final int DEFAULT_EXPIRATION_TIME = 60 * 24; //24 hours
    
    public static final int DEFAULT_PASSWORD_RESET = 20;
    
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(columnDefinition = "CHAR(32)")
    @Id
    private String token;
    
    @Column(name="expiration")
    @Type(type="timestamp")
    private Date expirationDate;
    
    @Enumerated(EnumType.STRING)
    private VerificationType type;
    
    private String email;
    
    public VerificationToken() {
        expirationDate = calculateExpirationDate(DEFAULT_EXPIRATION_TIME);
    }
    
    public VerificationToken(Credential credential, VerificationType type, int minutesToExpire) {
        expirationDate = calculateExpirationDate(minutesToExpire);
        email = credential.getEmail();
        this.type = type;
    }
    
    public String getToken() {
        return token;
    }
    
    public Date getExpirationDate() {
        return expirationDate;
    }
    
    public VerificationType getVerificationType() {
        return type;
    }
    
    public String getEmail() {
        return email;
    }
    
    public Date calculateExpirationDate(int minutesFromNow) {
        Date now = new Date();
        return new Date(now.getTime() + (minutesFromNow * 1000 * 60));
    }
    
    public boolean hasExpired() {
        Date tokenTime = new Date(getExpirationDate().getTime());
        return tokenTime.after(new Date());
    }
}
