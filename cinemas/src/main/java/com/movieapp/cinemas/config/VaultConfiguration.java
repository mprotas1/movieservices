package com.movieapp.cinemas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
class VaultConfiguration {

    @Bean
    public VaultTemplate vaultTemplate() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();

        vaultEndpoint.setHost("127.0.0.1");
        vaultEndpoint.setPort(8200);
        vaultEndpoint.setScheme("http");

        return new VaultTemplate(
                vaultEndpoint,
                new TokenAuthentication("root-token"));
    }

}
