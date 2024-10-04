package com.movieapp.cinemas.domain.repository;

import org.springframework.stereotype.Repository;

@Repository
public class DatabaseCinemaRoomRepository {
    private final JpaCinemaRoomRepository jpaCinemaRoomRepository;

    public DatabaseCinemaRoomRepository(JpaCinemaRoomRepository jpaCinemaRoomRepository) {
        this.jpaCinemaRoomRepository = jpaCinemaRoomRepository;
    }


}
