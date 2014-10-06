package com.pd.api.exception;

/**
 * Exception used when the user input is malformed or incorrect
 * 
 * @author tin
 *
 */
public class BadRequestException extends RuntimeException{
    
    private static final String DEFAULT_FIELD = "N/A";

    private String error;
    private String message;
    private String field;

    public BadRequestException(String error) {this(error, error);}
    public BadRequestException(String error, String message) {this(error, message, BadRequestException.DEFAULT_FIELD);}
    public BadRequestException(String error, String message, String field) {
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
