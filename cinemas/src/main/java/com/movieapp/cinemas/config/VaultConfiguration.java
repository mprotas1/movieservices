package com.movieapp.cinemas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.vault.authentication.TokenAuthentication;
import org.springframework.vault.client.VaultEndpoint;
import org.springframework.vault.core.VaultTemplate;

@Configuration
class VaultConfiguration {
    @Value("${vault.host}")
    private String vaultHost;

    @Value("${vault.port}")
    private int vaultPort;

    @Bean
    public VaultTemplate vaultTemplate() {
        VaultEndpoint vaultEndpoint = new VaultEndpoint();
        vaultEndpoint.setHost(vaultHost);
        vaultEndpoint.setPort(vaultPort);
        vaultEndpoint.setScheme("http");

        return new VaultTemplate(
                vaultEndpoint,
                new TokenAuthentication("root-token"));
    }

}
