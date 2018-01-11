package com.rbc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.app.domain.AppCode;

/*
 * [Geleric Logic] Interface to db's operation which is inherited from JpaRepository 
 * [Table] APPCODE
 */
public interface AppCodeRepository extends JpaRepository<AppCode, String> {

	AppCode findByCode(String code);
}
