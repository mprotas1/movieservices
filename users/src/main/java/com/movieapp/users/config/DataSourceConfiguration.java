package com.movieapp.users.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
class DataSourceConfiguration {
    @Value("${spring.datasource.url}")
    private String DB_ADDRESS;
    @Value("${spring.datasource.username}")
    private String DB_USER;
    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Bean
    @Primary
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create()
                .url(DB_ADDRESS)
                .username(DB_USER)
                .password(DB_PASSWORD)
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
