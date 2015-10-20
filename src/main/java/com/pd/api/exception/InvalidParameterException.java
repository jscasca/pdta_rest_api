package com.pd.api.exception;

/**
 * Exception used when the request received and invalid parameter that broke something
 * 
 * @author tin
 *
 */
public class InvalidParameterException extends ApiException {
    
    public InvalidParameterException() {super();}
    public InvalidParameterException(String message){ super(message);}
    public InvalidParameterException(String message, Throwable t) {super(message, t);}
    public InvalidParameterException(Throwable t) {super(t);}
}
