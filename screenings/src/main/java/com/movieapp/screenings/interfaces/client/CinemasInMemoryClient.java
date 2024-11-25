package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import org.springframework.context.annotation.Profile;

import java.util.Optional;

@Profile("test")
class CinemasInMemoryClient implements CinemasClient {

    @Override
    public Optional<ScreeningRoomDTO> getScreeningRoomById(ScreeningRoomId screeningRoomId) {
        return Optional.empty();
    }

}
