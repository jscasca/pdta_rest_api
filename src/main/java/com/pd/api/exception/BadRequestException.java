package com.pd.api.exception;

/**
 * Exception used when the user input is malformed or incorrect
 * 
 * @author tin
 *
 */
public class BadRequestException extends ApiException {
    
    public BadRequestException() {super();}
    public BadRequestException(String message){ super(message);}
    public BadRequestException(String message, Throwable t) {super(message, t);}
    public BadRequestException(Throwable t) {super(t);}
}
