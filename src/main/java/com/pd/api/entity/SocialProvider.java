package com.pd.api.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name="social_provider", 
    uniqueConstraints = { 
        @UniqueConstraint(columnNames = { "name" })})
public class SocialProvider {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id = null;
    
    private String name;
    
    public SocialProvider() {}
    public SocialProvider(String name) {
        this.name = providerNameToLowerCase(name);
    }
    
    public String getProviderName() {
        return this.name;
    }
    
    public static String providerNameToLowerCase(String name) {
        return name.toLowerCase();
    }
    
    public String toString() {
        return id + ":" + name;
    }
}
