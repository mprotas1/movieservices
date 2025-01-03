package com.movieapp.reservations.application.dto;

import java.util.List;
import java.util.UUID;

public record ReservationCreateRequest(UUID screeningId,
                                       List<UUID> seatIds,
                                       Long userId) {}