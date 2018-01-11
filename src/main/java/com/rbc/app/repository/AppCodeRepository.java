package com.rbc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.app.domain.AppCode;

public interface AppCodeRepository extends JpaRepository<AppCode, Integer> {

	AppCode findByCode(Integer code);
}
