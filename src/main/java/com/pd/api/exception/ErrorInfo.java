package com.pd.api.exception;

public class ErrorInfo {

    private String url;
    private String message;

    public ErrorInfo(String url, String msg) {
        this.url = url;
        this.message = msg;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setMessage(String msg) {
        this.message = msg;
    }
}
