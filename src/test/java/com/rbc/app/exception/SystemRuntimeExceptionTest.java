package com.rbc.app.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class SystemRuntimeExceptionTest {
    
    @Test
    public void testConstructor_withMessage_shouldReturnMessage() {
        SystemRuntimeException ex = new SystemRuntimeException("Test Exception");
        assertEquals("Test Exception", ex.getMessage());
    }
    
    @Test
    public void testConstructor_withException_shouldReturnCause() {
        Exception rootCause = new Exception("rootCause");
        SystemRuntimeException ex = new SystemRuntimeException(rootCause);
        assertEquals(rootCause, ex.getCause());
    }
    
    @Test
    public void testConstructor_withMessageAndException_shouldReturnCauseAndMessage() {
        Exception rootCause = new Exception("rootCause");
        SystemRuntimeException ex = new SystemRuntimeException("testMessage", rootCause);
        assertEquals(rootCause, ex.getCause());
        assertEquals("testMessage", ex.getMessage());
    }
}
