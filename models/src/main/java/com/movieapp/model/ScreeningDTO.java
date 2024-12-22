package com.movieapp.model;

import java.time.Instant;
import java.util.UUID;

public record ScreeningDTO(UUID screeningId,
                           Long movieId,
                           UUID screeningRoomId,
                           UUID cinemaId,
                           Instant startTime,
                           Instant endTime,
                           String movieTitle) {}
