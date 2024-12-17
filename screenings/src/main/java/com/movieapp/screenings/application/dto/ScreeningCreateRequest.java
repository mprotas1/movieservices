package com.movieapp.screenings.application.dto;

import java.time.Instant;
import java.util.UUID;

public record ScreeningCreateRequest(Long movieId, UUID screeningRoomId, Instant startTime) {}
