package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "cinemas")
@Data
@NoArgsConstructor
public class Cinema {
    @EmbeddedId
    private CinemaId id;
    private String name;
    @Embedded
    private Coordinates coordinates;

    @Embedded
    private Address address;
    @OneToMany(mappedBy = "cinemaId",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<CinemaRoom> rooms;

    public Cinema(String name, Address address, Coordinates coordinates) {
        Assert.notNull(name, "Cinema name must not be null");
        Assert.hasText(name, "Cinema name must not be empty");
        Assert.notNull(address, "Cinema address must not be null");
        this.id = new CinemaId();
        this.name = name;
        this.address = address;
        this.coordinates = coordinates;
        this.rooms = new ArrayList<>();
    }

    public UUID getIdValue() {
        return id.getUuid();
    }

    public void addRoom(CinemaRoom room) {
        Assert.notNull(room, "Cinema room must not be null");
        Assert.isTrue(!rooms.contains(room), "Cinema room already exists in the cinema: " + this.getName());
        rooms.add(room);
    }

    public void removeRoom(CinemaRoom room) {
        this.rooms.remove(room);
    }

    public int getNextRoomNumber() {
        List<Integer> roomNumbers = rooms.stream()
                .map(CinemaRoom::getNumber)
                .sorted()
                .toList();

        int nextNumber = 1;
        for (int roomNumber : roomNumbers) {
            if (roomNumber == nextNumber) {
                nextNumber++;
            } else {
                break;
            }
        }

        return nextNumber;
    }

}
