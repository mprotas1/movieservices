package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;

import java.util.List;
import java.util.Optional;

public interface CinemaRoomRepository {
    List<CinemaRoom> findByCinemaId(String cinemaId);
    Optional<CinemaRoom> findById(CinemaRoomId cinemaRoomId);
    Optional<CinemaRoom> findByCinemaAndNumber(String cinemaId, int number);
}
