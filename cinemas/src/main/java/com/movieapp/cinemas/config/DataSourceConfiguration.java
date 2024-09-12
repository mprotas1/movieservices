package com.movieapp.cinemas.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfiguration {

    @Bean
    public DataSource dataSource(@Qualifier(value = "jdbcConnectionDetailsForMysql") JdbcConnectionDetails jdbcConnectionDetails) {
        return DataSourceBuilder.create()
                .url(jdbcConnectionDetails.getJdbcUrl())
                .username(jdbcConnectionDetails.getUsername())
                .password(jdbcConnectionDetails.getPassword())
                .build();
    }

}
