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
import com.pd.api.exception.InvalidAuthenticationException;
import com.pd.api.exception.InvalidParameterException;
import com.pd.api.exception.InvalidStateException;

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
        return new ResponseEntity<ErrorInfo>(ex.getErrorInformation(req.getRequestURL().toString()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorInfo> duplicateResource(HttpServletRequest req, DuplicateResourceException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInformation(req.getRequestURL().toString()), HttpStatus.CONFLICT);
    }
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorInfo> badRequest(HttpServletRequest req, DuplicateResourceException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInformation(req.getRequestURL().toString()), HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidStateException.class)
    public ResponseEntity<ErrorInfo> invalidState(HttpServletRequest req, InvalidStateException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInformation(req.getRequestURL().toString()), HttpStatus.PRECONDITION_FAILED);
    }
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidAuthenticationException.class)
    public ResponseEntity<ErrorInfo> invalidAuth(HttpServletRequest req, InvalidAuthenticationException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInformation(req.getRequestURL().toString()), HttpStatus.UNAUTHORIZED);
    }
    
    /**
     * 
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ErrorInfo> invalidParameter(HttpServletRequest req, InvalidAuthenticationException ex) {
        return new ResponseEntity<ErrorInfo>(ex.getErrorInformation(req.getRequestURL().toString()), HttpStatus.NOT_ACCEPTABLE);
    }
    
}
