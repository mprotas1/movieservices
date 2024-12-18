package com.movieapp.reservations.interfaces.client;

import com.movieapp.model.ScreeningDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class ScreeningRestClient implements ScreeningClient {
    @Value("${services.url.screenings}")
    private final String SCREENING_SERVICE_URL;
    private final RestTemplate restTemplate;

    @Override
    public Optional<ScreeningDTO> getScreening(UUID id) {
        try {
            ResponseEntity<ScreeningDTO> response = restTemplate.getForEntity(SCREENING_SERVICE_URL + "/" + id, ScreeningDTO.class);

            if (response.getStatusCode().isError()) {
                log.debug("Error while fetching screening with id: {}", id);
                return Optional.empty();
            }

            log.debug("Successfully fetched screening with id: {}", id);
            return Optional.ofNullable(response.getBody());
        }
        catch (Exception e) {
            log.error("Error while fetching screening with id: {}", id, e);
            return Optional.empty();
        }
    }

}
