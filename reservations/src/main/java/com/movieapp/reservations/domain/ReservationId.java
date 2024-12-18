package com.movieapp.reservations.domain;

import lombok.Data;

import java.util.UUID;

@Data
public class ReservationId {
    private UUID id;

    private ReservationId() {
        this.id = UUID.randomUUID();
    }

    public ReservationId(UUID id) {
        this.id = id;
    }

    public static ReservationId generate() {
        return new ReservationId();
    }

}
