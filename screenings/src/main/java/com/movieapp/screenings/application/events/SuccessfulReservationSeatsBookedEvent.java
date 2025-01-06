package com.movieapp.screenings.application.events;

import java.util.UUID;

public record SuccessfulReservationSeatsBookedEvent(UUID reservationId) {}
