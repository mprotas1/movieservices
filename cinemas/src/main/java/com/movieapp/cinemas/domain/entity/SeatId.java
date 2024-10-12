package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Embeddable
@Getter
@Setter
public class SeatId {
    private UUID id;

    public SeatId() {
        this.id = UUID.randomUUID();
    }

    public SeatId(UUID id) {
        this.id = id;
    }
}
