package com.movieapp.screenings.infrastructure.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSource postgreSQLDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5443/screeningsdb")
                .username("user")
                .password("secret")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

}
