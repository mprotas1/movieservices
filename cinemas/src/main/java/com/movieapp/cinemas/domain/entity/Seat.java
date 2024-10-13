package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "seats")
@Data
public class Seat {
    @EmbeddedId
    private SeatId id;
    @Embedded
    private SeatPosition position;
    @Enumerated(EnumType.STRING)
    private SeatType seatType;
    @ManyToOne
    @JoinColumn(name = "cinema_room_id", nullable = false)
    private CinemaRoom room;
}
