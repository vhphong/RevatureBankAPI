package com.pnk.bankapi.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;


@Configuration
public class DatabaseConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource primaryDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        ((HikariDataSource) dataSource).setJdbcUrl("jdbc:postgresql://localhost:5432/bankapidb");
        return dataSource;
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource secondaryDataSource() {
        DataSource dataSource = DataSourceBuilder.create().build();
        ((HikariDataSource) dataSource).setJdbcUrl("jdbc:mysql://localhost:3306/bankapidb");
        return dataSource;
    }

}
