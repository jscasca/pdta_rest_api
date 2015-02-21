package com.pd.api.exception;

public class GeneralException extends RuntimeException {

    private final String error;
    private final String message;
    
    /**
     * Internal Server Exception
     * @param error
     * @param message
     */
    public GeneralException(String error, String message) {
        this.error = error;
        this.message = message;
    }
    
    public String getError() {
        return error;
    }
    
    public String getMessage() {
        return message;
    }
    
    public ErrorInfo getErrorInfo(String url) {
        ErrorInfo errorInfo = new ErrorInfo(url, message);
        return errorInfo;
    }
}
