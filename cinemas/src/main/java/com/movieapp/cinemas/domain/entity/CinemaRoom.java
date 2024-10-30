package com.movieapp.cinemas.domain.entity;

import com.movieapp.cinemas.domain.strategy.CreateSeatsStrategy;
import com.movieapp.cinemas.domain.strategy.DefaultCreateSeatsStrategy;
import com.movieapp.cinemas.domain.strategy.DefaultUpdateSeatsStrategy;
import com.movieapp.cinemas.domain.strategy.UpdateSeatsStrategy;
import jakarta.persistence.*;
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

    @JoinColumn(name = "cinema_id", nullable = false)
    @AttributeOverrides({
            @AttributeOverride(name = "uuid", column = @Column(name = "cinema_id", nullable = false))
    })
    private CinemaId cinemaId;

    @OneToMany(mappedBy = "room", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Seat> seats;

    private transient CreateSeatsStrategy seatsStrategy;

    public CinemaRoom(int number, int capacity, Cinema cinemaId) {
        checkCinemaRoomConstraints(number, capacity, cinemaId);
        this.id = new CinemaRoomId();
        this.number = number;
        this.capacity = capacity;
        this.cinemaId = cinemaId.getId();
        this.seatsStrategy = new DefaultCreateSeatsStrategy();
        createSeats();
    }

    public void updateCapacity(int capacity) {
        Assert.isTrue(capacity > 0, String.format("Cinema room capacity [%d] must be greater than 0", capacity));
        Assert.isTrue(this.capacity != capacity, String.format("Cinema room capacity [%d] is the same as the new capacity", capacity));
        UpdateSeatsStrategy strategy = new DefaultUpdateSeatsStrategy(this);
        strategy.updateSeats(capacity);
        this.capacity = this.getSeats().size();
    }

    private void checkCinemaRoomConstraints(int number, int capacity, Cinema cinema) {
        Assert.isTrue(number > 0, "Cinema room number must be greater than 0");
        Assert.isTrue(capacity > 0, "Cinema room capacity must be greater than 0");
        Assert.notNull(cinema, "Cinema must not be null");
    }

    public boolean exceedsCapacity(int numberOfSeats) {
        return numberOfSeats >= capacity;
    }

    private void createSeats() {
        this.seats = seatsStrategy.createSeats(this);
    }

}
