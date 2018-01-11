package com.rbc.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.rbc.app.domain.AppCode;
import com.rbc.app.domain.Version;
import com.rbc.app.domain.VersionId;
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
	public void testGetDataListByAppCodeAndOrderByVersionDescShouldReturnEmptyStringValue() {
		when(appCodeRepository.findByCode(code)).thenReturn(null);
		
		String data = service.getDataListByAppCodeAndOrderByVersionDesc(code);
		assertEquals("", data);
		verify(appCodeRepository).findByCode(code);
	}
}
