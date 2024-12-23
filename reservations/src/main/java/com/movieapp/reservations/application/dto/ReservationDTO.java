package com.movieapp.reservations.application.dto;

import java.util.UUID;

public record ReservationDTO(UUID id,
                             UUID screeningId,
                             UUID seatId,
                             Long userId,
                             String status) {}
