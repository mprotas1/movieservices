package com.movieapp.screenings.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.id.factory.internal.UUIDGenerationTypeStrategy;

import java.util.UUID;

@Entity
@Table(name = "screening_seats")
@Getter
@Setter
public class ScreeningSeatEntity {
    @Id
    private UUID id;
    private UUID screeningId;
    private UUID seatId;
    private int row;
    private int column;
    private boolean isReserved;
}
