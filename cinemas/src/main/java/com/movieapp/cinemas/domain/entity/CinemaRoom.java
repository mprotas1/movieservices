package com.movieapp.cinemas.domain.entity;

import com.movieapp.cinemas.domain.strategy.CreateSeatsStrategy;
import com.movieapp.cinemas.domain.strategy.DefaultCreateSeatsStrategy;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "room", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Seat> seats;

    private transient CreateSeatsStrategy seatsStrategy;

    public CinemaRoom(int number, int capacity, Cinema cinema) {
        checkCinemaRoomConstraints(number, capacity, cinema);
        this.id = new CinemaRoomId();
        this.number = number;
        this.capacity = capacity;
        this.cinema = cinema;
        this.seatsStrategy = new DefaultCreateSeatsStrategy();
        createSeats();
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

    public boolean exceedsCapacity(int a) {
        return a > capacity;
    }

    private void createSeats() {
        this.seats = seatsStrategy.createSeats(this);
    }

}
