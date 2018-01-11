package com.rbc.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbc.app.domain.AppCode;
import com.rbc.app.domain.Version;
import com.rbc.app.domain.VersionId;
import com.rbc.app.exception.SystemRuntimeException;
import com.rbc.app.repository.AppCodeRepository;
import com.rbc.app.repository.VersionRepository;


@RunWith(SpringJUnit4ClassRunner.class)
public class AppCodeServiceTest {

	private AppCodeService service;
	private AppCodeRepository appCodeRepository;
	private VersionRepository versionRepository;
	private String code = "1";
	private String ver = "0.0.1";
	
	@Before
	public void setUp() {
		appCodeRepository = mock(AppCodeRepository.class);
		versionRepository = mock(VersionRepository.class);
		service = new AppCodeService(appCodeRepository, versionRepository);
	}
	
	@After
	public void tearDown() {
		service=null;
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullAppCodeRepositoryShouldThrowNullPointerException() {
		new AppCodeService(null, versionRepository);
	}
	
	@Test(expected = NullPointerException.class)
	public void testConstructorWithNullVersionRepositoryShouldThrowNullPointerException() {
		new AppCodeService(appCodeRepository, null);
	}
	
	@Test
	public void testGetDataByAppCodeAndVersionShouldReturnStringValue() {
		VersionId id =new VersionId();
		id.setCode(code);
		id.setVersion(ver);
		Version mockVersion = mock(Version.class);
		when(versionRepository.findOne(id)).thenReturn(mockVersion);
		
		String data = service.getDataByAppCodeAndVersion(code, ver);
		assertNotNull(data);
		verify(versionRepository).findOne(id);
	}
	
	@Test
	public void testGetDataByAppCodeAndVersionShouldReturnEmptyStringValue() {
		VersionId id =new VersionId();
		id.setCode(code);
		id.setVersion(ver);
		when(versionRepository.findOne(id)).thenReturn(null);
		
		String data = service.getDataByAppCodeAndVersion(code, ver);
		assertEquals("", data);
		verify(versionRepository).findOne(id);
	}
	
	@Test
	public void testGetDataListByAppCodeAndOrderByVersionDescShouldReturnStringValue() {
		AppCode mockAppCode = mock(AppCode.class);
		when(appCodeRepository.findByCode(code)).thenReturn(mockAppCode);
		
		String data = service.getDataListByAppCodeAndOrderByVersionDesc(code);
		assertNotNull(data);
		verify(appCodeRepository).findByCode(code);
	}
	
	@Test
	public void testGetDataListByAppCodeAndOrderByVersionDescWithNullAppCodeShouldReturnEmptyStringValue() {
		when(appCodeRepository.findByCode(code)).thenReturn(null);
		
		String data = service.getDataListByAppCodeAndOrderByVersionDesc(code);
		assertEquals("", data);
		verify(appCodeRepository).findByCode(code);
	}
	
	@Test
	public void testGetDataListByAppCodeAndOrderByVersionDescWithNullVersionShouldReturnEmptyStringValue() {
		AppCode mockAppCode = mock(AppCode.class);
		when(appCodeRepository.findByCode(code)).thenReturn(mockAppCode);
		when(mockAppCode.getVersions()).thenReturn(null);
		
		String data = service.getDataListByAppCodeAndOrderByVersionDesc(code);
		assertEquals("", data);
		verify(appCodeRepository).findByCode(code);
		verify(mockAppCode).getVersions();
	}
	
	@Test
	public void testGetDataListByAppCodeAndOrderByVersionDescWithVersionShouldReturnJSonArray() throws Exception {
		AppCode mockAppCode = mock(AppCode.class);
		@SuppressWarnings("unchecked")
		List<Version> mockList = mock(List.class);
		Version mockVersion = mock(Version.class);
		
		when(appCodeRepository.findByCode(code)).thenReturn(mockAppCode);
		when(mockAppCode.getVersions()).thenReturn(mockList);
		when(mockList.size()).thenReturn(1);
		when(mockList.get(0)).thenReturn(mockVersion);
		//when(mockVersion.getUse()).thenReturn("0");
		
		Object obj = mock(Object.class);
		ObjectMapper om = mock(ObjectMapper.class);
		when(om.writeValueAsString(obj)).thenReturn("");
		
		String result = service.getDataListByAppCodeAndOrderByVersionDesc(code);
		assertNotEquals("", result);
		verify(appCodeRepository).findByCode(code);
		verify(mockAppCode, times(3)).getVersions();
	}

	@Test
	public void testSaveWithCodeAndVerAndDataToInsertShouldReturnValidStringValue() {
		VersionId id = new VersionId();
		id.setCode(code);
		id.setVersion(ver);
		when(versionRepository.findOne(id)).thenReturn(null);
		String data = "{\"name\":\"Gerrard\",\"age\":\"30\"}";
		String returnedData = service.save(code, ver, data);
		assertNotEquals(data, returnedData);
		verify(versionRepository).findOne(id);
	}
	
	@Test
	public void testSaveWithCodeAndVerAndDataToUpdateShouldReturnValidStringValue() {
		VersionId id = new VersionId();
		id.setCode(code);
		id.setVersion(ver);
		
		Version mockVersion = mock(Version.class);
		when(versionRepository.findOne(id)).thenReturn(mockVersion);
		String data = "{\"name\":\"Gerrard\",\"age\":\"30\"}";
		String returnedData = service.save(code, ver, data);
		assertNotEquals(data, returnedData);
		verify(versionRepository).findOne(id);
	}
	
	@Test
	public void testConvertObjectToJSonWithNullShouldReturnEmptyStringValue() {
		Object obj = null;
		String result = service.convertObjectToJSon(obj);
		assertEquals("", result);
	}

	@Test(expected = SystemRuntimeException.class)
	public void testConvertObjectToJSonShouldReturnException() throws Exception {
		Object obj = mock(Object.class);
		ObjectMapper om = mock(ObjectMapper.class);
		when(om.writeValueAsString(obj)).thenThrow(new JsonProcessingException("Parse exception") {
			private static final long serialVersionUID = 1L;});
		
		service.convertObjectToJSon(obj);
	}
}
