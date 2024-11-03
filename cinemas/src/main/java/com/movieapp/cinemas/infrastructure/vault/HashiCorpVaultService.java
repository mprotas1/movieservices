package com.movieapp.cinemas.infrastructure.vault;

import org.springframework.stereotype.Service;
import org.springframework.vault.core.VaultTemplate;

@Service
class HashiCorpVaultService implements VaultService {
    private final VaultTemplate vaultTemplate;

    HashiCorpVaultService(VaultTemplate vaultTemplate) {
        this.vaultTemplate = vaultTemplate;
    }

    @Override
    public String getGoogleGeocodingKey() {
        return vaultTemplate.opsForVersionedKeyValue("secret")
                .get("googlemaps")
                .getData()
                .get("apiKey")
                .toString();
    }

}
