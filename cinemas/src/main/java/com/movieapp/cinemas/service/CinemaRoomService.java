package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;

import java.util.List;
import java.util.UUID;

public interface CinemaRoomService {
    CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation);
    CinemaRoomDTO updateCapacity(CinemaRoomId roomId, int newCapacity);
    List<CinemaRoomDTO> findByCinemaId(CinemaId cinemaId);
    void deleteRoom(CinemaRoomId roomId);
    void deleteByNumber(CinemaId cinemaId, int roomNumber);
}
