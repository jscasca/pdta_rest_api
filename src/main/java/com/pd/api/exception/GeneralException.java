package com.pd.api.exception;

public class GeneralException extends RuntimeException {

    private final String error;
    private final String type;
    
    public GeneralException(String error, String type) {
        this.error = error;
        this.type = type;
    }
    
    public String getError() {
        return error;
    }
    
    public String getType() {
        return type;
    }
}
