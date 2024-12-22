package com.movieapp.reservations.interfaces.client;

import com.movieapp.model.SeatDTO;

import java.util.Optional;
import java.util.UUID;

public interface SeatClient {
    Optional<SeatDTO> getSeat(UUID id);
}
