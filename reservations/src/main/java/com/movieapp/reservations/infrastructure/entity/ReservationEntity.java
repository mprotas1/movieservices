package com.movieapp.reservations.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class ReservationEntity {
    @Id
    private UUID id;
    private UUID screeningId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ReservationSeatEntity> seats;
    private Long userId;
    private String status;
    private Double price;

    public ReservationEntity() {}

    public ReservationEntity(UUID id, UUID screeningId, List<UUID> seatIds, Long userId, String status, Double price) {
        this.id = id;
        this.screeningId = screeningId;
        this.seats = createSeatEntities(seatIds);
        this.userId = userId;
        this.status = status;
        this.price = price;
    }

    public ReservationEntity(UUID id, UUID screeningId, List<UUID> seatIds, Long userId, String status) {
        this.id = id;
        this.screeningId = screeningId;
        this.seats = createSeatEntities(seatIds);
        this.userId = userId;
        this.status = status;
    }

    private List<ReservationSeatEntity> createSeatEntities(List<UUID> seatIds) {
        return seatIds.stream()
                .map(seatId -> new ReservationSeatEntity(seatId, this))
                .toList();
    }

}
