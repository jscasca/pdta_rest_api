package com.pd.api.exception;

/**
 * 
 * @author tin
 *
 */
public class InvalidStateException extends ApiException {

    public InvalidStateException() {super();}
    public InvalidStateException(String message){ super(message);}
    public InvalidStateException(String message, Throwable t) {super(message, t);}
    public InvalidStateException(Throwable t) {super(t);}
}
