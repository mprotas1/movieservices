package com.movieapp.reservations.application.events;

import java.util.UUID;

public record PaymentStatusEvent(UUID reservationId, String status, Long userId) {
}
