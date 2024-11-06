package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface JpaScreeningRepository extends JpaRepository<Screening, ScreeningId> {
    List<Screening> findAllByMovieId(MovieId movieId);
    List<Screening> findAllByScreeningId(ScreeningId screeningId);
}
