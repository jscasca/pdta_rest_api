package com.pd.api.exception;

public class DuplicateResourceException extends ApiException {
    
    public DuplicateResourceException() {super();}
    public DuplicateResourceException(String message){ super(message);}
    public DuplicateResourceException(String message, Throwable t) {super(message, t);}
    public DuplicateResourceException(Throwable t) {super(t);}
}
