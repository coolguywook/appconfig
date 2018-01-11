package com.rbc.app.repository;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.rbc.app.domain.AppCode;
import com.rbc.app.domain.Version;
import com.rbc.app.domain.VersionId;

@RunWith(SpringRunner.class)
@Transactional
@DataJpaTest
public class VersionRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;
	
	@Autowired
	private AppCodeRepository appCodeRepository;

    @Autowired
    private VersionRepository versionRepository;

    @Test
    public void testInsertVersionShouldReturnExpectedVersioni() throws Exception {
    	this.entityManager.persist(new AppCode("1", new Timestamp(System.currentTimeMillis())));
        AppCode appCode = this.appCodeRepository.findOne("1");
        
    	VersionId id = new VersionId();
    	id.setCode(appCode.getCode());
    	id.setVersion("0.0.1");
    	
    	Version version = new Version();
    	version.setVersionId(id);
    	version.setData("{\"name\":\"Gerrard\",\"age\":\"30\"}");
    	
        this.entityManager.persist(version);
        Version returnVersion = this.versionRepository.findOne(id);
        assertEquals("1", returnVersion.getVersionId().getCode());
        assertThat(returnVersion.getData()).isEqualToIgnoringCase("{\"name\":\"Gerrard\",\"age\":\"30\"}");
    }
}
