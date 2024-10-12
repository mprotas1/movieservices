package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity(name = "seats")
@Data
public class Seat {
    @EmbeddedId
    private SeatId id;
    @Embedded
    private SeatPosition position;
    @ManyToOne
    private CinemaRoom room;
}
