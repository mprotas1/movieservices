package com.movieapp.screenings.application.dto;

import java.time.Instant;
import java.util.UUID;

public record ScreeningDTO(UUID screeningId, UUID movieId, UUID screeningRoomId, Instant startTime, Instant endTime) {}
