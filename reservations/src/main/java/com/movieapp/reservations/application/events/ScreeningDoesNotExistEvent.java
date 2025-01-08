package com.movieapp.reservations.application.events;

import java.util.UUID;

public record ScreeningDoesNotExistEvent(UUID screeningId, UUID reservationId) {}
