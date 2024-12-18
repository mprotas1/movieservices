package com.movieapp.reservations.application.dto;

import java.util.UUID;

public record ReservationCreateRequest(UUID screeningId, UUID seatId, Long userId) {}
