package com.movieapp.screenings.infrastructure.persistence;

import com.movieapp.screenings.application.mapper.ScreeningMapper;
import com.movieapp.screenings.domain.model.*;
import com.movieapp.screenings.domain.repository.ScreeningRepository;
import com.movieapp.screenings.infrastructure.entity.ScreeningEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
class SQLScreeningRepository implements ScreeningRepository {
    private final JpaScreeningRepository repository;
    private final ScreeningMapper screeningMapper;

    SQLScreeningRepository(JpaScreeningRepository repository, ScreeningMapper screeningMapper) {
        this.repository = repository;
        this.screeningMapper = screeningMapper;
    }

    @Override
    public Screening save(Screening screening) {
        ScreeningEntity entity = repository.save(screeningMapper.domainModelToEntity(screening));
        return screeningMapper.entityToDomainModel(entity);
    }

    @Override
    public Optional<Screening> findById(ScreeningId screeningId) {
        return repository.findById(screeningId.id())
                .map(screeningMapper::entityToDomainModel);
    }

    @Override
    public List<Screening> findAll() {
        return repository.findAll().stream()
                .map(screeningMapper::entityToDomainModel)
                .toList();
    }

    @Override
    public List<Screening> findAllByMovieId(MovieId movieId) {
        return repository.findAllByMovieId(movieId.id()).stream()
                .map(screeningMapper::entityToDomainModel)
                .toList();
    }

    @Override
    public List<Screening> findAllByMovieAndCinemaId(MovieId movieId, CinemaId cinemaId) {
        return repository.findAllByMovieIdAndCinemaId(movieId.id(), cinemaId.id()).stream()
                .map(screeningMapper::entityToDomainModel)
                .toList();
    }

    @Override
    public List<Screening> findAllByScreeningRoomId(ScreeningRoomId screeningRoomId) {
        return repository.findAllByScreeningRoomId(screeningRoomId.id()).stream()
                .map(screeningMapper::entityToDomainModel)
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
