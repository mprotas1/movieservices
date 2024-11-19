package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.ScreeningRoomDTO;
import com.movieapp.screenings.domain.model.ScreeningRoomId;

public interface ScreeningRoomClient {
    ScreeningRoomDTO getScreeningRoomById(ScreeningRoomId id);
}
