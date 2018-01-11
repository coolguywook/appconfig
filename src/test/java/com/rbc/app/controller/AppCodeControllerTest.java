package com.rbc.app.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rbc.app.domain.Response;
import com.rbc.app.domain.Success;
import com.rbc.app.service.AppCodeService;

@RunWith(SpringJUnit4ClassRunner.class)
public class AppCodeControllerTest {
	private AppCodeController controller;
	private AppCodeService service;
	private MockHttpServletRequest httpRequest;
	private String code = "1";
	private String ver = "0.0.1";

	@Before
	public void setUp() {
		service = mock(AppCodeService.class);
		controller = new AppCodeController(service);
		httpRequest = new MockHttpServletRequest();
	}
	
	@After
	public void tearDown() {
		controller = null;
	}
	
	@Test
	public void testGetDataWithAllValidInputsShouldReturnSuccess() {		
		when(service.getDataByAppCodeAndVersion(code, ver)).thenReturn("");
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Success> postResponseEntity = (ResponseEntity<Success>) controller.getData(code, ver, httpRequest);
		assertEquals(200, postResponseEntity.getStatusCode().value());
		verify(service).getDataByAppCodeAndVersion(code, ver);
	}
	
	@Test
	public void testGetDataListWithAllValidInputsShouldReturnSuccess() {		
		when(service.getDataListByAppCodeAndOrderByVersionDesc(code)).thenReturn("");
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Success> postResponseEntity = (ResponseEntity<Success>) controller.getDataList(code, httpRequest);
		assertEquals(200, postResponseEntity.getStatusCode().value());
		verify(service).getDataListByAppCodeAndOrderByVersionDesc(code);
	}
	
	@Test
	public void testPostDataListWithAllValidInputsShouldReturnSuccess() {		
		String data = "{\"name\":\"Gerrard\",\"age\":\"30\"}";
		when(service.save(code, ver, data)).thenReturn("");
		
		@SuppressWarnings("unchecked")
		ResponseEntity<Success> postResponseEntity = (ResponseEntity<Success>) controller.postData(code, ver, data, httpRequest);
		assertEquals(200, postResponseEntity.getStatusCode().value());
		verify(service).save(code, ver, data);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testPostDataWithInvalidParameterShouldReturnIllegalArgumentException() {
		Success mockResponse = mock(Success.class);
		mockResponse.setStatus(400);
		
		controller.postData(code, ver, null, httpRequest);
	}
	
}
