package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@AllArgsConstructor
class ScreeningRoomRestClient implements ScreeningRoomClient {
    private final RestTemplate restTemplate;

    @Override
    public ScreeningRoomDTO getScreeningRoomById(ScreeningRoomId id) {
        ResponseEntity<ScreeningRoomDTO> roomResponseEntity = restTemplate.getForEntity("http://cinemas-service/cinemas/rooms/" + id, ScreeningRoomDTO.class);

        if(roomResponseEntity.getStatusCode().isError()) {
            throw new RuntimeException("Error while fetching screening room with id: " + id);
        }

        return roomResponseEntity.getBody();
    }

}
