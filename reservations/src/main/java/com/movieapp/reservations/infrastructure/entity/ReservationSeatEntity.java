package com.movieapp.reservations.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "reservation_seats")
@Getter
@Setter
@NoArgsConstructor
public class ReservationSeatEntity {
    @Id
    private UUID id;
    private UUID screeningSeatId;
    @ManyToOne
    @JoinColumn(name = "reservation_id")
    private ReservationEntity reservation;

    public ReservationSeatEntity(UUID screeningSeatId, ReservationEntity reservationEntity) {
        this.id = UUID.randomUUID();
        this.screeningSeatId = screeningSeatId;
        this.reservation = reservationEntity;
    }

}
