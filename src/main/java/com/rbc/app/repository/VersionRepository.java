package com.rbc.app.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.rbc.app.domain.Version;
import com.rbc.app.domain.VersionId;

/*
 * [Geleric Logic] Interface to db's operation which is inherited from JpaRepository.
 * [Table] Version
 */
public interface VersionRepository extends JpaRepository<Version, VersionId> {

}
