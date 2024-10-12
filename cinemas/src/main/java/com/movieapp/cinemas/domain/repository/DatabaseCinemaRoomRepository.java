package com.movieapp.cinemas.domain.repository;

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
    private final JpaCinemaRoomRepository jpaRepository;

    @Override
    public CinemaRoom save(CinemaRoom cinemaRoom) {
        return jpaRepository.save(cinemaRoom);
    }

    @Override
    public List<CinemaRoom> findByCinemaId(String cinemaId) {
        return jpaRepository.findByCinemaId(cinemaId);
    }

    @Override
    public Optional<CinemaRoom> findById(CinemaRoomId cinemaRoomId) {
        return jpaRepository.findById(cinemaRoomId);
    }

    @Override
    public Optional<CinemaRoom> findByCinemaAndNumber(String cinemaId, int number) {
        return Optional.empty();
    }

    @Override
    public void deleteById(CinemaRoomId cinemaRoomId) {
        jpaRepository.deleteById(cinemaRoomId);
    }

}
