package com.movieapp.reservations.application.dto;

import java.util.List;
import java.util.UUID;

public record ScreeningSeatsAlreadyLockedDTO(UUID reservationId, List<UUID> screeningSeatIds) {}