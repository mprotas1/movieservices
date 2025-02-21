package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.service.PaymentStatus;

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

}
