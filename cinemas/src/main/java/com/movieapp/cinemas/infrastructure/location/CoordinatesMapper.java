package com.movieapp.cinemas.infrastructure.location;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.cinemas.domain.entity.Coordinates;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoordinatesMapper {
    private final ObjectMapper mapper;

    public Coordinates extractCoordinates(String json) throws JsonProcessingException {
        JsonNode root = mapper.readTree(json);

        double latitude = root.path("results")
                .get(0)
                .path("geometry")
                .path("location").path("lat")
                .asDouble();
        double longitude = root.path("results")
                .get(0).path("geometry")
                .path("location")
                .path("lng")
                .asDouble();

        return new Coordinates(latitude, longitude);
    }

}
