package com.rbc.app.domain;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "appcode")
public class AppCode {

	 @Id
	 @GeneratedValue(strategy=GenerationType.AUTO)
	 @Column(name = "code")
	 private Integer code;

	 @Column(name = "name")
	 private String name;
	  
	 @Column(name = "c_datetime")
	 private Timestamp cDatetime;
	 
	 @OneToMany(mappedBy = "appCode", cascade = CascadeType.ALL)
	 private Set<Version> versions;
	 
	 public AppCode(String name, Timestamp cDatetime) {
		 this.name = name;
		 this.cDatetime = cDatetime;
	 }
}
