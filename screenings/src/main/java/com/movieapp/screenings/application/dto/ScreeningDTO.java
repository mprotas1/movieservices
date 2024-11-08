package com.movieapp.screenings.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ScreeningDTO(UUID screeningId, UUID movieId, UUID screeningRoomId, LocalDateTime startTime, LocalDateTime endTime) {}
