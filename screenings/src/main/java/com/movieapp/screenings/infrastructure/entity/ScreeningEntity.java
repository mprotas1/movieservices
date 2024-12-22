package com.movieapp.screenings.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "screenings")
@Data
public class ScreeningEntity {
    @Id
    @GeneratedValue
    private UUID id;
    private Long movieId;
    private UUID screeningRoomId;
    private Instant startTime;
    private Instant endTime;
    private String movieTitle;
    private int screeningRoomNumber;
}
