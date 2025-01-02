package com.movieapp.screenings.infrastructure.entity;

import com.movieapp.screenings.application.dto.SeatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.id.factory.internal.UUIDGenerationTypeStrategy;

import java.util.UUID;

@Entity
@Table(name = "screening_seats")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScreeningSeatEntity {
    @Id
    private UUID id;
    private UUID screeningId;
    private int row;
    @Column(name = "seat_column")
    private int column;
    @Enumerated(EnumType.STRING)
    private SeatType type;
    private boolean isReserved;
}
