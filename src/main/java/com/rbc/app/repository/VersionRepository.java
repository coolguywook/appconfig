package com.rbc.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.app.domain.Version;
import com.rbc.app.domain.VersionId;

public interface VersionRepository extends JpaRepository<Version, VersionId> {

	
}
