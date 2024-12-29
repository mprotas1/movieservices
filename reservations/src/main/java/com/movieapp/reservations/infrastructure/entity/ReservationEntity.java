package com.movieapp.reservations.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class ReservationEntity {
    @Id
    private UUID id;
    private UUID screeningId;
    private UUID seatId;
    private Long userId;
    private String status;

    public ReservationEntity() {}

    public ReservationEntity(UUID screeningId, UUID seatId, Long userId, String status) {
        this.screeningId = screeningId;
        this.seatId = seatId;
        this.userId = userId;
        this.status = status;
    }

}
