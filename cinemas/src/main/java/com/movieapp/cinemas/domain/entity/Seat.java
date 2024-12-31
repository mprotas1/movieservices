package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "seats")
@NoArgsConstructor
@Getter
@Setter
@ToString
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

    public Seat(SeatId id, SeatPosition position, SeatType type, CinemaRoom room) {
        this.id = id;
        this.position = position;
        this.seatType = type;
        this.room = room;
    }

    public Seat(SeatPosition position, SeatType type, CinemaRoom room) {
        this.id = new SeatId();
        this.position = position;
        this.seatType = type;
        this.room = room;
    }

}
