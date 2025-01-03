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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SeatEntity> seats;
    private Long userId;
    private String status;

    public ReservationEntity() {}

    public ReservationEntity(UUID id, UUID screeningId, List<UUID> seatIds, Long userId, String status) {
        this.id = id;
        this.screeningId = screeningId;
        this.seats = createSeatEntities(seatIds);
        this.userId = userId;
        this.status = status;
    }

    private List<SeatEntity> createSeatEntities(List<UUID> seatIds) {
        return seatIds.stream().map(seatId -> {
            SeatEntity seatEntity = new SeatEntity();
            seatEntity.setId(seatId);
            return seatEntity;
        }).toList();
    }

}
