package com.movieapp.cinemas.infrastructure.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.movieapp.cinemas.domain.entity.Coordinates;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Primary
@Profile("!test")
class GoogleCinemaLocationService implements CinemaLocationService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Value("${googlemaps.api-key}")
    private String API_KEY;
    private final String BASE_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=";
    private final RestTemplate restTemplate;
    private final CoordinatesMapper coordinatesMapper;

    GoogleCinemaLocationService(RestTemplate restTemplate, CoordinatesMapper coordinatesMapper) {
        this.restTemplate = restTemplate;
        this.coordinatesMapper = coordinatesMapper;
    }

    @Override
    public Coordinates getCoordinates(String city, String street, String postalCode) {
        String address = city + ", " + street + ", " + postalCode;
        log.debug("Getting coordinates for address: {}", address);
        String url = BASE_URL + address + "&key=" + API_KEY;
        ResponseEntity<String> locationResponse = restTemplate.getForEntity(url, String.class);

        if(!locationResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to get coordinates for address: {}", address);
            throw new LocationException("Failed to get coordinates for address: " + address);
        }

        log.debug("Google API location response: {}", locationResponse.getBody());
        return getCoordinatesFromResponse(locationResponse.getBody());
    }

    private Coordinates getCoordinatesFromResponse(String jsonResponse) {
        try {
            return coordinatesMapper.extractCoordinates(jsonResponse);
        }
        catch (JsonProcessingException e) {
            log.error("Failed to extract coordinates from response: {}", jsonResponse);
            throw new LocationException("Failed to extract coordinates from response: " + jsonResponse);
        }
    }

}
