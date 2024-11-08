package com.movieapp.screenings.infrastructure.entity;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.model.ScreeningTime;
import jakarta.persistence.*;
import lombok.Data;

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
    @Embedded
    private ScreeningTime time;

    public Screening toDomain() {
        return new Screening(
                new MovieId(movieId),
                new ScreeningRoomId(screeningRoomId),
                time
        );
    }

    public static ScreeningEntity fromDomain(Screening screening) {
        ScreeningEntity entity = new ScreeningEntity();
        entity.id = screening.getScreeningId().id();
        entity.movieId = screening.getMovieId().id();
        entity.screeningRoomId = screening.getScreeningRoomId().id();
        entity.time = screening.getTime();
        return entity;
    }

}
