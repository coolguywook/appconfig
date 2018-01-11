package com.rbc.app.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.rbc.app.controller.ServiceExceptionHandler;
import com.rbc.app.exception.SystemRuntimeException;

@RunWith(SpringJUnit4ClassRunner.class)
public class ServiceExceptionHandlerTest {
	private ServiceExceptionHandler handler;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	private MessageSource messageSource;
	
	@Before
	public void setup() {
		handler = new ServiceExceptionHandler();
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
		messageSource = mock(MessageSource.class);
		ReflectionTestUtils.setField(handler, "messageSource", messageSource);
	}
	

    @Test
    public void testHandleIllegalArgumentExceptionShouldSetStatusToBadRequest() throws IOException  {
        ResponseEntity<Object> entity = handler.handleIllegalArgumentException(new IllegalArgumentException(""), request, response);
        com.rbc.app.domain.Error error = (com.rbc.app.domain.Error)entity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), entity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
    }
    
    @Test
    public void testHandleRuntimeExceptionShouldSetStatusToInternalServerError() throws IOException  {
        ResponseEntity<Object> entity = handler.handleSystemRuntimeException(new RuntimeException(""), request, response);
        com.rbc.app.domain.Error error = (com.rbc.app.domain.Error)entity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), entity.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getStatus());
    }

    @Test
    public void testHandleSystemRuntimeExceptionShouldSetStatusToInternalServerError() throws IOException  {
        ResponseEntity<Object> entity = handler.handleSystemRuntimeException(new SystemRuntimeException(""), request, response);
        com.rbc.app.domain.Error error = (com.rbc.app.domain.Error)entity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), entity.getStatusCodeValue());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), error.getStatus());
    }

    @Test
    public void testHandleHttpMessageNotReadableExceptionShouldSetStatusToBadRequest() throws IOException  {
        ResponseEntity<Object> entity = handler.handleHttpMessageNotReadableException(new HttpMessageNotReadableException(""), request, response);
        com.rbc.app.domain.Error error = (com.rbc.app.domain.Error)entity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), entity.getStatusCodeValue());
        assertEquals(HttpStatus.BAD_REQUEST.value(), error.getStatus());
    }
    
}
