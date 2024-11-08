package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.domain.model.MovieId;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningId;
import com.movieapp.screenings.domain.model.ScreeningRoomId;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;
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
        return repository.save(ScreeningEntity.fromDomain(screening)).toDomain();
    }

    @Override
    public Optional<Screening> findById(ScreeningId screeningId) {
        return repository.findById(screeningId.id())
                .map(ScreeningEntity::toDomain);
    }

    @Override
    public List<Screening> findAll() {
        return repository.findAll().stream()
                .map(ScreeningEntity::toDomain)
                .toList();
    }

    @Override
    public List<Screening> findAllByMovieId(MovieId movieId) {
        return repository.findAllByMovieId(movieId.id()).stream()
                .map(ScreeningEntity::toDomain)
                .toList();
    }

    @Override
    public List<Screening> findAllByScreeningRoomId(ScreeningRoomId screeningRoomId) {
        return repository.findAllByScreeningRoomId(screeningRoomId.id()).stream()
                .map(ScreeningEntity::toDomain)
                .toList();
    }

    @Override
    public boolean existsById(ScreeningId screeningId) {
        return repository.existsById(screeningId.id());
    }

    @Override
    public void deleteById(ScreeningId screeningId) {
        repository.deleteById(screeningId.id());
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
