package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "cinemas")
@Data
public class Cinema {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            optional = false)
            @JoinColumn(name = "address_id") Address address;
    @OneToMany(mappedBy = "cinema",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<CinemaRoom> rooms = new ArrayList<>();

    public static Cinema create(String name, Address address) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setAddress(address);
        return cinema;
    }

    public void addRoom(CinemaRoom room) {
        rooms.add(room);
    }

}
