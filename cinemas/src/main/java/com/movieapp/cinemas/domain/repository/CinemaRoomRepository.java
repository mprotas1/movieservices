package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.CinemaRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CinemaRoomRepository extends JpaRepository<CinemaRoom, Long> {

    @Query("SELECT cinema_room FROM CinemaRoom cinema_room WHERE cinema_room.cinema.id = :cinemaId")
    List<CinemaRoom> findByCinemaId(Long cinemaId);

}
