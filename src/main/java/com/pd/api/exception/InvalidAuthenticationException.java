package com.pd.api.exception;

public class InvalidAuthenticationException extends ApiException {
    
    public InvalidAuthenticationException() {super();}
    public InvalidAuthenticationException(String message) { super(message);}
    public InvalidAuthenticationException(String message, Throwable t){super(message, t);}
    public InvalidAuthenticationException(Throwable t) {super(t);}
}
