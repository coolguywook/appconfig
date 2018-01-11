package com.rbc.app.exception;


import com.google.common.base.Preconditions;

/*
 * [Generic Logic] User defined runtime exception. 
 */
public class SystemRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SystemRuntimeException(String message) {
	    super(message);
	    Preconditions.checkNotNull(message);
	}
	
	public SystemRuntimeException(Throwable cause) {
	    super(cause);
	    Preconditions.checkNotNull(cause);
	}
	
	public SystemRuntimeException(String message, Throwable cause) {
	    super(message, cause);
	    Preconditions.checkNotNull(message);
	    Preconditions.checkNotNull(cause);
	}
}
