package com.pd.api.entity.aux;

public class MemberRegistration {

    private String name;
    
    private String displayName;
    
    private String password;
    
    public MemberRegistration(){}
    public MemberRegistration(String n, String d, String p) {
        name = n;
        displayName = d;
        password = p;
    }
    
    public void setName(String n) {
        name = n;
    }
    
    public void setDisplayName(String d) {
        displayName = d;
    }
    
    public void setPassword(String p) {
        password = p;
    }
    
    public String getName() {
        return name;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getPassword() {
        return password;
    }
}
