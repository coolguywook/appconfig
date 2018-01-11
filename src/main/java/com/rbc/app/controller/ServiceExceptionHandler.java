package com.rbc.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rbc.app.exception.SystemRuntimeException;


@ControllerAdvice
public class ServiceExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);
    
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
    	this.messageSource = messageSource;
    }
    
    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	String exceptionMsg = messageSource.getMessage("exception.illegal.argument", null, Locale.US);
    	com.rbc.app.domain.Error error = populateError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exceptionMsg, e, request);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
      
    @ExceptionHandler({ SystemRuntimeException.class, RuntimeException.class })
    public ResponseEntity<Object> handleSystemRuntimeException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String exceptionMsg = messageSource.getMessage("exception.runtime", null, Locale.US);
        com.rbc.app.domain.Error error = populateError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), exceptionMsg, e, request);
        return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Exception e, HttpServletRequest request,  HttpServletResponse response) throws IOException {
    	String exceptionMsg = messageSource.getMessage("exception.bad.request", null, Locale.US);
    	com.rbc.app.domain.Error error = populateError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exceptionMsg, e, request);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    private com.rbc.app.domain.Error populateError(int status, String statusMsg, String exceptionMsg, Exception e, HttpServletRequest request) {
    	com.rbc.app.domain.Error error = new com.rbc.app.domain.Error();
    	
    	error.setTimestamp(new Date().getTime());
    	error.setStatus(status);
    	error.setPath(request.getRequestURI());
    	error.setError(statusMsg);
    	error.setException(e.getMessage());
    	error.setMessage(exceptionMsg);

    	return error;
    }

}
