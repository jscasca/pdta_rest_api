package com.pd.api.exception;

public class ErrorInfo {

    private String url;
    private String message;
    private String field;
    
    public ErrorInfo(String url, String msg) {this(url, msg, "N/A");}
    public ErrorInfo(String url, String msg, String field) {
        this.url = url;
        this.message = msg;
        this.field = field;
    }
    
    public String getUrl() {
        return url;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getField() {
        return field;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public void setMessage(String msg) {
        this.message = msg;
    }
    
    public void setField(String field) {
        this.field = field;
    }
}
