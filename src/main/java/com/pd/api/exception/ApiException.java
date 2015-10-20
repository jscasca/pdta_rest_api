package com.pd.api.exception;

/**
 * 
 * @author tin
 *
 */
public class ApiException extends RuntimeException {
    
    public ApiException() {super();}
    public ApiException(String message){ super(message);}
    public ApiException(String message, Throwable t) {super(message, t);}
    public ApiException(Throwable t) {super(t);}
    
    public ErrorInfo getErrorInformation(String url) {
        return new ErrorInfo(url, getMessage());
    }
}
