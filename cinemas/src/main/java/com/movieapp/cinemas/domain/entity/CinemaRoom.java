package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity @Table(name = "cinema_rooms")
@Data
public class CinemaRoom {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String number;
    private int capacity;
    private @ManyToOne @JoinColumn(name = "cinema_id",
                                   nullable = false) Cinema cinema;

    public static CinemaRoom roomInCinema(Cinema cinema, int capacity, Long number) {
        CinemaRoom room = new CinemaRoom();
        room.setCapacity(capacity);
        room.setNumber(String.valueOf(number));
        room.setCinema(cinema);
        cinema.addRoom(room);
        return room;
    }

}
