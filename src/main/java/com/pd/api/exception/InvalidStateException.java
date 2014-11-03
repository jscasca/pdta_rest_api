package com.pd.api.exception;

public class InvalidStateException extends RuntimeException {

    private static final String DEFAULT_STATE = "N/A";
    
    private String error;
    private String message;
    private String state;
    
    public InvalidStateException(String error) {this(error, error);}
    public InvalidStateException(String error, String message) {this(error, message, DEFAULT_STATE);}
    public InvalidStateException(String error, String message, String state) {
        this.error = error;
        this.message = message;
        this.state = state;
    }
    
    public String getError() {
        return error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getState() {
        return state;
    }
    
    public ErrorInfo getErrorInfo(String url) {
        return new ErrorInfo(url, message, state);
    }
}
