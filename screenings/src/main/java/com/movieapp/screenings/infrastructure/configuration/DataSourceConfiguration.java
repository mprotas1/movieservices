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

    @Value("${app.db.username}")
    private String username;

    @Value("${app.db.password}")
    private String password;

    @Bean
    @Primary
    public DataSource postgreSQLDataSource() {
        return DataSourceBuilder.create()
                .url(address)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver")
                .build();
    }

}
