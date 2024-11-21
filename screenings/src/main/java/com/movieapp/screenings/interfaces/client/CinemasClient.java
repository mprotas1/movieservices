package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import java.util.Optional;

public interface CinemasClient {
    Optional<ScreeningRoomDTO> getScreeningRoomById(ScreeningRoomId id);
}
