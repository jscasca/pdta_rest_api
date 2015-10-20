package com.pd.api.exception;

public class GeneralException extends ApiException {

    public GeneralException(){super();}
    public GeneralException(String message){super(message);}
    public GeneralException(String message, Throwable t){super(message, t);}
    public GeneralException(Throwable t){super(t);}
}
