package com.pd.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pd.api.exception.ErrorInfo;
import com.pd.api.exception.GeneralException;

@ControllerAdvice
public class RestExceptionProcessor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ErrorInfo> internalError(HttpServletRequest req, GeneralException ex) {
        String errorMessage = "";
        
        errorMessage += ex.getError();
        String errorURL = req.getRequestURL().toString();
        
        ErrorInfo e = new ErrorInfo(errorURL, errorMessage);
        return new ResponseEntity<ErrorInfo>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
