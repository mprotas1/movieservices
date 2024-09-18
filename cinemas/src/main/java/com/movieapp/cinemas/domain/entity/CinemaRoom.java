package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "cinema_rooms")
@Data
public class CinemaRoom {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private int number;
    private int capacity;
    private @ManyToOne @JoinColumn(name = "cinema_id",
                                   nullable = false) Cinema cinema;

    public static CinemaRoom roomInCinema(Cinema cinema, int capacity, int number) {
        CinemaRoom room = new CinemaRoom();
        room.setCapacity(capacity);
        room.setNumber(number);
        room.setCinema(cinema);
        cinema.addRoom(room);
        return room;
    }

}
