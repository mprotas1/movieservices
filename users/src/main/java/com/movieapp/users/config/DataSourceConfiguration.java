package com.movieapp.users.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
class DataSourceConfiguration {

    @Bean
    @Primary
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:postgresql://localhost:5432/usersdb")
                .username("user")
                .password("secret")
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean
    public Flyway flyway(@Qualifier("postgresqlDataSource") DataSource dataSource) {
        return Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true)
                .load();
    }

    @Bean
    public ApplicationRunner migrateFlyway(Flyway flyway) {
        return args -> flyway.migrate();
    }

}
