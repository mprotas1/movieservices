package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
@Profile({"dev", "prod"})
class CinemasRestClient implements CinemasClient {
    private final RestTemplate restTemplate;

    @Override
    public Optional<ScreeningRoomDTO> getScreeningRoomById(ScreeningRoomId screeningRoomId) {
        ResponseEntity<ScreeningRoomDTO> roomResponseEntity = restTemplate.getForEntity(buildUrl(screeningRoomId), ScreeningRoomDTO.class);

        if(roomResponseEntity.getStatusCode().isError()) {
            log.error("Error while fetching screening room with screeningRoomId: {}", screeningRoomId);
            return Optional.empty();
        }

        log.debug("Fetched screening room with screeningRoomId: {}", screeningRoomId);
        return Optional.ofNullable(roomResponseEntity.getBody());
    }

    private String buildUrl(ScreeningRoomId screeningRoomId) {
        return "http://cinemas-service/cinemas/rooms/" + screeningRoomId.id().toString();
    }

}
