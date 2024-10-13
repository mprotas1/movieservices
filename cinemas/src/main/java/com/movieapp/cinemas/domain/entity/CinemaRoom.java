package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.List;

@Entity
@Table(name = "cinema_rooms")
@Data
@NoArgsConstructor
public class CinemaRoom {
    @EmbeddedId
    private CinemaRoomId id;

    private int number;

    private int capacity;

    @ManyToOne
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @OneToMany(mappedBy = "room", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Seat> seats;

    public CinemaRoom(int number, int capacity, Cinema cinema) {
        checkCinemaRoomConstraints(number, capacity, cinema);
        this.id = new CinemaRoomId();
        this.number = number;
        this.capacity = capacity;
        this.cinema = cinema;
        createSeats();
    }

    private void createSeats() {

    }

    public void updateCapacity(int capacity) {
        Assert.isTrue(capacity > 0, String.format("Cinema room capacity [%d] must be greater than 0", capacity));
        this.capacity = capacity;
    }

    private void checkCinemaRoomConstraints(int number, int capacity, Cinema cinema) {
        Assert.isTrue(number > 0, "Cinema room number must be greater than 0");
        Assert.isTrue(capacity > 0, "Cinema room capacity must be greater than 0");
        Assert.notNull(cinema, "Cinema must not be null");
    }

}
