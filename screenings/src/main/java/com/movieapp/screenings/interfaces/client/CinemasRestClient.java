package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
class CinemasRestClient implements CinemasClient {
    @Value("${cinemas.service.url}")
    private String CINEMAS_SERVICE_URL;
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
        return CINEMAS_SERVICE_URL + "/rooms/" + screeningRoomId.id().toString();
    }

}
