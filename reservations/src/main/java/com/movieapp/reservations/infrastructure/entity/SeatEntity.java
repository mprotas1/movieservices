package com.movieapp.reservations.infrastructure.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class SeatEntity {
    @Id
    private UUID id;
    @ManyToOne
    private SeatEntity reservationId;
}
