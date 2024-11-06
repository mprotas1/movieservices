package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class SQLScreeningRepository implements ScreeningRepository {
    private final JpaScreeningRepository repository;

    SQLScreeningRepository(JpaScreeningRepository repository) {
        this.repository = repository;
    }

    @Override
    public Screening save(Screening screening) {
        return repository.save(screening);
    }

    @Override
    public Optional<Screening> findById(ScreeningId screeningId) {
        return repository.findById(screeningId);
    }

    @Override
    public List<Screening> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Screening> findAllByMovieId(MovieId movieId) {
        return repository.findAllByMovieId(movieId);
    }

    @Override
    public List<Screening> findAllByScreeningId(ScreeningId screeningId) {
        return repository.findAllByScreeningId(screeningId);
    }

    @Override
    public boolean existsById(ScreeningId screeningId) {
        return repository.existsById(screeningId);
    }

    @Override
    public void deleteById(ScreeningId screeningId) {
        repository.deleteById(screeningId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
