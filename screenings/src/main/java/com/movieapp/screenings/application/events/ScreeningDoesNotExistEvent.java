package com.movieapp.screenings.application.events;

import java.util.UUID;

public record ScreeningDoesNotExistEvent(UUID screeningId, UUID reservationId) {}
