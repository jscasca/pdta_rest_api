package com.pd.api.exception;

public class DuplicateResourceException extends RuntimeException {
    
    private static final String DEFAULT_FIELD = "N/A";

    private String error;
    private String message;
    private String field;
    
    public DuplicateResourceException(String error){this(error, error);}
    public DuplicateResourceException(String error, String message){this(error, message, DuplicateResourceException.DEFAULT_FIELD);}
    public DuplicateResourceException(String error, String message, String field) {
        this.error = error;
        this.message = message;
        this.field = field;
    }
    
    public String getError() {
        return error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public String getField() {
        return field;
    }
    
    public ErrorInfo getErrorInfo(String url) {
        return new ErrorInfo(url, message, field);
    }
}
