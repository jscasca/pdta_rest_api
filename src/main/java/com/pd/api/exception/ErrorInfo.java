package com.pd.api.exception;

public class ErrorInfo {

    private String url;
    private String message;
    
    public ErrorInfo(String u, String m) {
        url = u;
        message = m;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setUrl(String u) {
        url = u;
    }
    
    public void setMessage(String m) {
        message = m;
    }
}
