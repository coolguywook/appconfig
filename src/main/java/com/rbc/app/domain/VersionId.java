package com.rbc.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class VersionId implements Serializable{

	private static final long serialVersionUID = 1L;
	 
	@Column(name = "version")
	private String version;
	
	@Column(name = "code")
	private Integer code;
}
