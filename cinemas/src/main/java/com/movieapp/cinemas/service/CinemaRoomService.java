package com.movieapp.cinemas.service;

import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;

import java.util.List;
import java.util.UUID;

public interface CinemaRoomService {
    CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation);
    CinemaRoomDTO updateCapacity(Long roomId, int newCapacity);
    List<CinemaRoomDTO> findByCinemaId(UUID cinemaId);
    void deleteRoom(Long roomId);
    void deleteByNumber(UUID cinemaId, int roomNumber);
}
