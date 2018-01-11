package com.rbc.app.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.rbc.app.exception.SystemRuntimeException;


@ControllerAdvice
public class ServiceExceptionHandler {

    //private static final Logger LOGGER = LoggerFactory.getLogger(ServiceExceptionHandler.class);
    
    private MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
    	this.messageSource = messageSource;
    }
    
	/*
	 * [Generic logic] build JSON response body for illegalArgument.
	 * [HTTP Status] 400
	 */
    @ExceptionHandler
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException e, HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	String exceptionMsg = messageSource.getMessage("exception.illegal.argument", null, Locale.US);
    	com.rbc.app.domain.Error error = populateError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exceptionMsg, e, request);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }
      
	/*
	 * [Generic logic] build JSON response body for user defined error and runtime exception.
	 * [HTTP Status] 500
	 */
    @ExceptionHandler({ SystemRuntimeException.class, RuntimeException.class })
    public ResponseEntity<Object> handleSystemRuntimeException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        String exceptionMsg = messageSource.getMessage("exception.runtime", null, Locale.US);
        com.rbc.app.domain.Error error = populateError(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), exceptionMsg, e, request);
        return new ResponseEntity<Object>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
	/*
	 * [Generic logic] build JSON response body for ugly request's body .
	 * [HTTP Status] 400
	 */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(Exception e, HttpServletRequest request,  HttpServletResponse response) throws IOException {
    	String exceptionMsg = messageSource.getMessage("exception.bad.request", null, Locale.US);
    	com.rbc.app.domain.Error error = populateError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exceptionMsg, e, request);
        return new ResponseEntity<Object>(error, HttpStatus.BAD_REQUEST);
    }

    /*
     *{
     * "timestamp": 1515712690425,
     * "status": 400,
     * "error": "BAD_REQUEST",
     * "exception": "Required request body is missing: public org.springframework.http.ResponseEntity<?> com.rbc.app.controller.AppCodeController.postData(java.lang.String,java.lang.String,java.lang.String,javax.servlet.http.HttpServletRequest)",
     * "message": "Bad Request",
     * "path": "/v1/api/1/config/0.0.1"
	 * } 
     * 
     * 
     */
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
