package com.movieapp.reservations.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
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

    @Override
    public String toString() {
        return id.toString();
    }

}
