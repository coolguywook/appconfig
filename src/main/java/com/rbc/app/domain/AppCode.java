package com.rbc.app.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude="versions")
@ToString(exclude = {"versions"})
@Entity
@Table(name = "appcode")
public class AppCode {

	 @Id
	 @Column(name = "code")
	 private String code;
	  
	 @Column(name = "c_datetime")
	 private Timestamp cDatetime;
	 
	 @OneToMany(mappedBy = "appCode", cascade = CascadeType.ALL)
	 @JsonManagedReference
	 private List<Version> versions;
	 
	 public AppCode() {		 
	 }
	 
	 public AppCode(String code, Timestamp cDatetime) {
		 this.code = code;
		 this.cDatetime = cDatetime;
	 }
}
