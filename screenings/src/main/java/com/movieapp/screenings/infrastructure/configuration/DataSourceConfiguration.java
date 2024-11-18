package com.movieapp.screenings.infrastructure.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
class DataSourceConfiguration {

    @Value("${app.db.address}")
    private String address;

    @Bean
    @Primary
    public DataSource postgreSQLDataSource() {
        return DataSourceBuilder.create()
                .url(address)
                .username("user")
                .password("secret")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

}
