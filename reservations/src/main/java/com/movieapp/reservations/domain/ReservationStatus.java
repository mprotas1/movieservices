package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.service.PaymentStatus;

import java.util.Arrays;
import java.util.Objects;

public enum ReservationStatus {
    PENDING,
    CONFIRMED,
    CANCELLED,
    BOOKED,
    PAID;

    public static ReservationStatus fromPaymentStatus(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case PAYMENT_SUCCESSFUL -> PAID;
            case PAYMENT_FAILED -> CANCELLED;
        };
    }

    public static ReservationStatus fromString(String status) {
        return Arrays.stream(values())
                .filter(reservationStatus -> Objects.equals(reservationStatus.name(), status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid status: " + status));
    }

}
