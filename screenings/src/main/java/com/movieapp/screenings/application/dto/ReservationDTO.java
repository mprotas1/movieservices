package com.movieapp.screenings.application.dto;

import java.util.List;
import java.util.UUID;

public record ReservationDTO(UUID id,
                             UUID screeningId,
                             List<UUID> seatIds,
                             Long userId,
                             String status) {}