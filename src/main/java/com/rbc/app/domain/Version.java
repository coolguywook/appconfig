package com.rbc.app.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude="appCode")
@ToString(exclude = {"appCode"})
@Entity
@Table(name = "version")
public class Version {

	@EmbeddedId
	private VersionId versionId;
	
	@Column(name = "use")
	private String use;
	
	@Column(name = "data")
	private String data;
	
	@Column(name = "c_datetime")
	private Timestamp cDatetime;
	
	@Column(name = "u_datetime")
	private Timestamp uDatetime;
	
	@ManyToOne
    @JoinColumn(name = "code", referencedColumnName = "code", insertable=false, updatable=false)
	@JsonBackReference
    private AppCode appCode;
}
