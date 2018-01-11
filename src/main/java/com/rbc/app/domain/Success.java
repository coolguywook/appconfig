package com.rbc.app.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Success {
	private long timestamp;
	private int status;
	private String message;
	private String path;
	private String data;
	
}

