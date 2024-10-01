package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity @Table(name = "cinema_rooms")
@Data
@NoArgsConstructor
public class CinemaRoom {
    private @EmbeddedId CinemaRoomId id;
    private int number;
    private int capacity;
    private @ManyToOne @JoinColumn(name = "cinema_id", nullable = false) Cinema cinema;

    public CinemaRoom(int number, int capacity, Cinema cinema) {
        checkCinemaRoomConstraints(number, capacity, cinema);
        this.number = number;
        this.capacity = capacity;
        this.cinema = cinema;
    }

    private void checkCinemaRoomConstraints(int number, int capacity, Cinema cinema) {
        Assert.isTrue(number > 0, "Cinema room number must be greater than 0");
        Assert.isTrue(capacity > 0, "Cinema room capacity must be greater than 0");
        Assert.notNull(cinema, "Cinema must not be null");
    }

}
