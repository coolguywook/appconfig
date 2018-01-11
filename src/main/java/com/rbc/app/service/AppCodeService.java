package com.rbc.app.service;

import java.util.Comparator;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import com.rbc.app.domain.AppCode;
import com.rbc.app.domain.Response;
import com.rbc.app.domain.Version;
import com.rbc.app.domain.VersionId;
import com.rbc.app.exception.SystemRuntimeException;
import com.rbc.app.repository.AppCodeRepository;
import com.rbc.app.repository.VersionRepository;

@Component("appCodeService")
public class AppCodeService {

	private final AppCodeRepository appCodeRepository;
	private final VersionRepository versionRepository;
	
	@Autowired
	public AppCodeService(@Qualifier("appCodeRepository") AppCodeRepository appCodeRepository
			, @Qualifier("versionRepository") VersionRepository versionRepository) {
		this.appCodeRepository = Preconditions.checkNotNull(appCodeRepository);
		this.versionRepository = Preconditions.checkNotNull(versionRepository);
	}
	
	public String getDataByAppCodeAndVersion(String code, String ver) {
		Preconditions.checkNotNull(code);
		Preconditions.checkNotNull(ver);
		
		VersionId id = new VersionId();
		id.setCode(code);
		id.setVersion(ver);
		
		Version returnedVersion = versionRepository.findOne(id);	
		if(returnedVersion != null) {
			Response res = new Response();
			res.setCode(code);
			res.setVersion(ver);
			res.setData(returnedVersion.getData());
			
			return convertObjectToJSon(res);		
		}
		return "";
	}
	
	public String getDataListByAppCodeAndOrderByVersionDesc(String code) {
		Preconditions.checkNotNull(code);
		AppCode appCode = appCodeRepository.findByCode(code);
		if(appCode==null) return "[]";
		List<Version> versions = appCode.getVersions().stream().filter(x -> "0".equals(x.getUse())).collect(Collectors.toList());
		//List<Version> versions = appCode.getVersions();
		Comparator<Version> versionComparator = (o1, o2)->o2.getUDatetime().compareTo(o1.getCDatetime());
		versions.sort(versionComparator);
		
		return convertObjectToJSon(versions);
	}
	
	@Transactional
	public String save(String code, String ver, String data) {
		Preconditions.checkNotNull(code);
		Preconditions.checkNotNull(ver);
		
		Date date = new Date();
		Timestamp currentTimestamp = new Timestamp(date.getTime());
		
		VersionId id = new VersionId();
		id.setCode(code);
		id.setVersion(ver);
		
		Version returnedVersion = versionRepository.findOne(id);
		if(returnedVersion == null) {
			
			AppCode appCode = new AppCode(code, currentTimestamp);
			
			appCodeRepository.save(appCode);
			String returnedCode = appCode.getCode();
			
			Version version = new Version();
			VersionId newId = new VersionId();
			newId.setCode(returnedCode);
			newId.setVersion(ver);
			
			version.setVersionId(newId);
			version.setData(data);
			version.setCDatetime(currentTimestamp);
			version.setUDatetime(currentTimestamp);
			version.setUse("0");
			
			versionRepository.save(version);
		} else {
			returnedVersion.setData(data);
			returnedVersion.setUDatetime(currentTimestamp);
			
			versionRepository.save(returnedVersion);
		}
		
		Response res = new Response();
		res.setCode(code);
		res.setVersion(ver);
		res.setData(data);
		
		return convertObjectToJSon(res);
	}
	
	
	private String convertObjectToJSon(Object obj) {
		if(obj == null) return "";
		ObjectMapper objectMapper = new ObjectMapper();
		String resJson = null;
		try {
			resJson = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new SystemRuntimeException(e.getMessage());
		}
		return resJson;
	}
}
