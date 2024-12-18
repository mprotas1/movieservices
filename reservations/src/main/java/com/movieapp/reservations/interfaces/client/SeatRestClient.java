package com.movieapp.reservations.interfaces.client;

import com.movieapp.model.SeatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class SeatRestClient implements SeatClient {
    @Value("${services.url.cinemas}")
    private final String SEATS_URL;
    private final RestTemplate restTemplate;

    @Override
    public Optional<SeatDTO> getSeat(UUID id) {
        log.info("Fetching seat with id: {}", id);
        try {
            return Optional.ofNullable(restTemplate.getForObject(SEATS_URL + "/seats/" + id, SeatDTO.class));
        }
        catch (HttpClientErrorException httpException) {
            log.error("Seat with id: {} not found", id);
            return Optional.empty();
        }
    }

}
