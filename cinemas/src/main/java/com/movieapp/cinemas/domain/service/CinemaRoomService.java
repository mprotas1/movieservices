package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.model.CinemaRoomDTO;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;

import java.util.List;

public interface CinemaRoomService {
    CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation);
    CinemaRoomDTO updateCapacity(Long roomId, int newCapacity);
    List<CinemaRoomDTO> findByCinemaId(Long cinemaId);
    void deleteRoom(Long roomId);
    void deleteByNumber(Long cinemaId, int roomNumber);
}
