package com.movieapp.screenings.domain.repository;

import com.movieapp.screenings.domain.model.*;

import java.util.List;
import java.util.Optional;

public interface ScreeningRepository {
    Screening save(Screening screening);

    Optional<Screening> findById(ScreeningId screeningId);
    List<Screening> findAll();
    List<Screening> findAllByMovieId(MovieId movieId);
    List<Screening> findAllByMovieAndCinemaId(MovieId movieId, CinemaId cinemaId);
    List<Screening> findAllByScreeningRoomId(ScreeningRoomId screeningRoomId);
    boolean existsById(ScreeningId screeningId);

    void deleteById(ScreeningId screeningId);
    void deleteAll();
}
