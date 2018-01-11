package com.rbc.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Preconditions;
import com.rbc.app.domain.Success;
import com.rbc.app.service.AppCodeService;

import static com.rbc.app.common.Constants.RESTFUL_VERSION;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import static com.rbc.app.common.Constants.RESTFUL_API;

@RestController
@RequestMapping(RESTFUL_VERSION + RESTFUL_API)
public class AppCodeController {

	//private static final Logger LOGGER = LoggerFactory.getLogger(AppCodeController.class);

	private AppCodeService appCodeService;
	private MessageSource messageSource;
	
	@Autowired
	public AppCodeController(@Qualifier("appCodeService") AppCodeService appCodeService,
			@Qualifier("messageSource") MessageSource messageSource) {
		this.appCodeService = Preconditions.checkNotNull(appCodeService);
		this.messageSource = Preconditions.checkNotNull(messageSource);
	}
	
	@RequestMapping(value="/{appCode}/config/{version:.+}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getData(
			@PathVariable("appCode") String code,
			@PathVariable("version") String ver, HttpServletRequest req) {
    	
		String resJson = appCodeService.getDataByAppCodeAndVersion(code, ver);
		Success response = successResponseMessage(
				"".equals(resJson) ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value(), resJson, req);
		return new ResponseEntity<Success>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{appCode}/config", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getDataList(
			@PathVariable("appCode") String code, HttpServletRequest req) {
    	
		String resJson = appCodeService.getDataListByAppCodeAndOrderByVersionDesc(code);
		Success response = successResponseMessage(
				"".equals(resJson) ? HttpStatus.NO_CONTENT.value() : HttpStatus.OK.value(), "".equals(resJson) ? "[]" : resJson, req);
		return new ResponseEntity<Success>(response, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{appCode}/config/{version:.+}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> postData(
			@PathVariable("appCode") String code,
			@PathVariable("version") String ver, 
			@RequestBody String data, HttpServletRequest req) {
    	Preconditions.checkArgument(isNotBlank(data), "Invalid Request");
    	String resJson = appCodeService.save(code, ver, data);
		Success response = successResponseMessage(HttpStatus.OK.value(), resJson, req);
		return new ResponseEntity<Success>(response, HttpStatus.OK);
	}
	
	private Success successResponseMessage(int status, String data, HttpServletRequest request) {
		Success success = new Success();
		success.setTimestamp(getCurrentTimestamp(new Date()));
		success.setStatus(status);
		String message = messageSource.getMessage("output.message.success." + status, null, Locale.US);
		success.setMessage(message);
		success.setPath(request.getRequestURI());
		success.setData(data);
		return success;
	}

	
    private long getCurrentTimestamp(Date date) {
    	Timestamp timestamp = new Timestamp(date.getTime());
    	return timestamp.getTime();
    }
}
