package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "cinemas")
@Data
@NoArgsConstructor
public class Cinema {
    private @EmbeddedId CinemaId id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "cinema",
               cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               orphanRemoval = true)
    private List<CinemaRoom> rooms;

    public Cinema(String name, Address address) {
        Assert.notNull(name, "Cinema name must not be null");
        Assert.notNull(address, "Cinema address must not be null");
        this.name = name;
        this.address = address;
        this.rooms = new ArrayList<>();
    }

    public boolean hasSameAddress(Cinema other) {
        return address.equals(other.address);
    }

    public void addRoom(CinemaRoom room) {
        Assert.notNull(room, "Cinema room must not be null");
        Assert.isTrue(!rooms.contains(room), "Cinema room already exists in the cinema: " + this.getName());
        rooms.add(room);
    }

    public boolean hasRooms() {
        return !rooms.isEmpty();
    }

    public int getTotalSeats() {
        throw new UnsupportedOperationException("Not implemented yet :)");
    }

    public void rename(String name) {
        Assert.notNull(name, "Cinema name must not be null");
        Assert.isTrue(!this.getName().equals(name), "Cinema name must be different");
        this.setName(name);
    }

    public void removeRoom(CinemaRoom room) {
        Assert.isTrue(rooms.contains(room), "Cinema room does not exist in the cinema: " + this.getName());
        rooms.remove(room);
    }

}
