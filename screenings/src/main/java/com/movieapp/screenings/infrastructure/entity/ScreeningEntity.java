package com.movieapp.screenings.infrastructure.entity;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;
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
    private UUID movieId;
    private UUID screeningRoomId;
    private Instant startTime;
    private Instant endTime;

}
