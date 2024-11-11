package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Profile("test")
class InMemoryScreeningRepository implements ScreeningRepository {
    private final Set<Screening> screenings = new HashSet<>();

    @Override
    public Screening save(Screening screening) {
        return screenings.add(screening) ? screening : null;
    }

    @Override
    public Optional<Screening> findById(ScreeningId screeningId) {
        return screeningId == null ? Optional.empty() : screenings.stream()
                .filter(screening -> screeningId.equals(screening.getScreeningId()))
                .findFirst();
    }

    @Override
    public List<Screening> findAll() {
        return new ArrayList<>(screenings);
    }

    @Override
    public List<Screening> findAllByMovieId(MovieId movieId) {
        return screenings.stream()
                .filter(screening -> movieId.equals(screening.getMovieId()))
                .toList();
    }

    @Override
    public List<Screening> findAllByScreeningRoomId(ScreeningRoomId screeningRoomId) {
        return screenings.stream()
                .filter(screening -> screeningRoomId.equals(screening.getScreeningRoomId()))
                .toList();
    }

    @Override
    public boolean existsById(ScreeningId screeningId) {
        return screenings.stream()
                .anyMatch(screening -> screeningId.equals(screening.getScreeningId()));
    }

    @Override
    public void deleteById(ScreeningId screeningId) {
        screenings.removeIf(screening -> screeningId.equals(screening.getScreeningId()));
    }

    @Override
    public void deleteAll() {
        screenings.clear();
    }

}
