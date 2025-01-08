package com.movieapp.reservations.application.events;

import java.util.UUID;

public record ReservationPaymentEvent(UUID reservationId, Long userId, Double amount) {}
