package com.rbc.app.domain;

import lombok.Data;

@Data
public class Error {
	private long timestamp;
	private int status;
	private String error;
	private String exception;
	private String message;
	private String path;
}
