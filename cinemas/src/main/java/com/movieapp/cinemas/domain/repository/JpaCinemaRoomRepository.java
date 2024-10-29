package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCinemaRoomRepository extends JpaRepository<CinemaRoom, CinemaRoomId> {
    @Query("SELECT cinema_room FROM CinemaRoom cinema_room WHERE cinema_room.cinemaId.uuid = :cinemaId")
    List<CinemaRoom> findByCinemaId(String cinemaId);

    @Query("DELETE FROM CinemaRoom cinema_room WHERE cinema_room.cinemaId.uuid = :cinemaId AND cinema_room.number = :roomNumber")
    void deleteByRoomNumber(CinemaId cinemaId, int roomNumber);
}
