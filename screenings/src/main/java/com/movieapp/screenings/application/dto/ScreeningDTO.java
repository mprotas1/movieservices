package com.movieapp.screenings.application.dto;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record ScreeningDTO(UUID screeningId,
                           Long movieId,
                           UUID screeningRoomId,
                           UUID cinemaId,
                           Instant startTime,
                           Instant endTime,
                           String movieTitle,
                           int screeningRoomNumber,
                           List<ScreeningSeatDTO> seats) {}
