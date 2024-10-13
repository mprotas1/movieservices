package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class DatabaseCinemaRoomRepository implements CinemaRoomRepository {
    private final JpaCinemaRoomRepository repository;

    @Override
    public CinemaRoom save(CinemaRoom cinemaRoom) {
        return repository.save(cinemaRoom);
    }

    @Override
    public List<CinemaRoom> findAll() {
        return repository.findAll();
    }

    @Override
    public List<CinemaRoom> findByCinemaId(CinemaId cinemaId) {
        return repository.findByCinemaId(cinemaId.getUuid().toString());
    }

    @Override
    public Optional<CinemaRoom> findById(CinemaRoomId cinemaRoomId) {
        return repository.findById(cinemaRoomId);
    }

    @Override
    public Optional<CinemaRoom> findByCinemaAndNumber(CinemaId cinemaId, int number) {
        return Optional.empty();
    }

    @Override
    public void deleteById(CinemaRoomId cinemaRoomId) {
        repository.deleteById(cinemaRoomId);
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }

}
