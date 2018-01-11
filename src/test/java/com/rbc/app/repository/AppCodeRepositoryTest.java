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

import com.rbc.app.domain.AppCode;

@RunWith(SpringRunner.class)
@DataJpaTest
public class AppCodeRepositoryTest {

	@Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AppCodeRepository repository;

    @Test
    public void testInsertAppCodeShouldReturnExpectedAppCode() throws Exception {
        this.entityManager.persist(new AppCode(1, new Timestamp(System.currentTimeMillis())));
        AppCode appCode = this.repository.findOne(1);
        assertEquals(1, appCode.getCode().intValue());
    }
}
