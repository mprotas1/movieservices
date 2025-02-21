package com.movieapp.reservations.application.service;

import java.util.Arrays;

public enum PaymentStatus {
    PAYMENT_FAILED,
    PAYMENT_SUCCESSFUL;

    public static PaymentStatus fromString(String status) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown status: " + status));
    }

}
