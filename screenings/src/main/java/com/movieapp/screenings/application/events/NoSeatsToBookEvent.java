package com.movieapp.screenings.application.events;

import java.util.UUID;

public record NoSeatsToBookEvent(UUID reservationId) {}