package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity(name = "cinemas")
@Data
public class Cinema {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private @OneToOne(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true,
            optional = false) Address address;

    public static Cinema create(String name, Address address) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setAddress(address);
        return cinema;
    }

}
