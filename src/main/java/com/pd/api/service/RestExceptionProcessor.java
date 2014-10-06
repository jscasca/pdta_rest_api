package com.pd.api.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.pd.api.exception.BadRequestException;
import com.pd.api.exception.DuplicateResourceException;
import com.pd.api.exception.ErrorInfo;
import com.pd.api.exception.GeneralException;

/**
 * This class handles exceptions by responding with an ErrorInfo object that carries additional information
 * and a meaningful http status code.
 * 
 * @author tin
 *
 */
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
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorInfo> duplicateResource(HttpServletRequest req, DuplicateResourceException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInfo(req.getRequestURL().toString()), HttpStatus.CONFLICT);
    }
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfo> badRequest(HttpServletRequest req, DuplicateResourceException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInfo(req.getRequestURL().toString()), HttpStatus.BAD_REQUEST);
    }
    
}
