package com.pd.api.security;

public class SocialLoginUsername {

    private String provider = "";
    private String id = "";
    
    public SocialLoginUsername(String username) {
        String[] aux = username.split(":");
        if(aux.length != 2) {}
        else {
            provider = aux[0];
            id = aux[1];
        }
    }
    
    public String getProvider() {
        return provider;
    }
    
    public String getId() {
        return id;
    }
}
