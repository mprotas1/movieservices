package com.movieapp.screenings.application.dto;

import java.time.Instant;
import java.util.UUID;

public record ScreeningDTO(UUID screeningId,
                           Long movieId,
                           UUID screeningRoomId,
                           Instant startTime,
                           Instant endTime,
                           String movieTitle) {}
