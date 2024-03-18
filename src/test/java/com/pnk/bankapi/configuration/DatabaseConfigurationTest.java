package com.pnk.bankapi.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertTrue;

@ContextConfiguration(classes = {DatabaseConfiguration.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseConfigurationTest {

    @Autowired
    private DatabaseConfiguration databaseConfiguration;

    @Test
    public void testPrimaryDataSource() {
        // Arrange, Act and Assert
        assertTrue(databaseConfiguration.primaryDataSource() instanceof HikariDataSource);
    }


    @Test
    public void testSecondaryDataSource() {
        // Arrange, Act and Assert
        assertTrue(databaseConfiguration.secondaryDataSource() instanceof HikariDataSource);
    }
}