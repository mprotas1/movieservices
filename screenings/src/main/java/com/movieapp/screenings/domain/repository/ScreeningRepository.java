package com.movieapp.screenings.domain.repository;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.model.ScreeningRoomId;

import java.util.List;
import java.util.Optional;

public interface ScreeningRepository {
    Screening save(Screening screening);

    Optional<Screening> findById(ScreeningId screeningId);
    List<Screening> findAll();
    List<Screening> findAllByMovieId(MovieId movieId);
    List<Screening> findAllByScreeningRoomId(ScreeningRoomId screeningRoomId);
    boolean existsById(ScreeningId screeningId);

    void deleteById(ScreeningId screeningId);
    void deleteAll();
}
