package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "seats")
@Data
@NoArgsConstructor
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

    public Seat(SeatPosition position, SeatType type, CinemaRoom room) {
        this.id = new SeatId();
        this.position = position;
        this.seatType = type;
        this.room = room;
    }

}
