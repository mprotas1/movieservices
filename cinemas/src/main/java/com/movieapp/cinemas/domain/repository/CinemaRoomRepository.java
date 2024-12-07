package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;

import java.util.List;
import java.util.Optional;

public interface CinemaRoomRepository {
    CinemaRoom save(CinemaRoom cinemaRoom);
    List<CinemaRoom> findAll();
    List<CinemaRoom> findByCinemaId(CinemaId cinemaId);
    Optional<CinemaRoom> findById(CinemaRoomId cinemaRoomId);
    Optional<CinemaRoom> findByCinemaAndNumber(CinemaId cinemaId, int number);
    void deleteById(CinemaRoomId cinemaRoomId);
    void deleteAll();
    void deleteByRoomNumber(CinemaId cinemaId, int roomNumber);
    void delete(CinemaRoom cinemaRoom);
}
