package com.movieapp.screenings.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScreeningCreateRequest(UUID movieId, int duration, UUID screeningRoomId, LocalDateTime startTime) {}
