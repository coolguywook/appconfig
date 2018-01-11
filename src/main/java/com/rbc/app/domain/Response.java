package com.rbc.app.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response {
	private String code;
	private String version;
	private String data;
}
