package com.movieapp.reservations.interfaces.client;

import com.movieapp.model.ScreeningDTO;

import java.util.Optional;
import java.util.UUID;

public interface ScreeningClient {
    Optional<ScreeningDTO> getScreening(UUID id);
}
