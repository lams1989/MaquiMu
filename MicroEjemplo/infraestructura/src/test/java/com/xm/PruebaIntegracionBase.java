package com.xm;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import com.zaxxer.hikari.HikariDataSource;

@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = ApplicationMock.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@Transactional
@EnableEncryptableProperties
public class PruebaIntegracionBase {

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private DataSource dataSource;

    private static HikariDataSource hikariDataSource;

    @BeforeAll
    public void saveDataSource() {
        hikariDataSource = (HikariDataSource) dataSource;
    }

    @AfterAll
    public static void afterAll() {
        if (hikariDataSource != null) {
            hikariDataSource.close();
        }
    }
}
