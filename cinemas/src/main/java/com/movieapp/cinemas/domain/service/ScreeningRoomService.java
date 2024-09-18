package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.model.CinemaRoomDTO;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
class ScreeningRoomService implements CinemaRoomService {
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    @Override
    @Transactional
    public CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation) {
        Cinema contextCinema = cinemaRepository.findById(roomInformation.cinemaId())
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + roomInformation.cinemaId() + " not found"));
        int roomNumber = getSequentialRoomNumber(contextCinema);
        CinemaRoom room = CinemaRoom.roomInCinema(contextCinema, roomInformation.capacity(), roomNumber);
        CinemaRoom savedRoom = cinemaRoomRepository.save(room);
        return new CinemaRoomDTO(contextCinema.getId(), savedRoom.getNumber(), savedRoom.getCapacity());
    }

    @Override
    public CinemaRoomDTO updateCapacity(Long roomId, int newCapacity) {
        CinemaRoom room = cinemaRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + roomId + " not found"));
        room.setCapacity(newCapacity);
        CinemaRoom updatedRoom = cinemaRoomRepository.save(room);
        return new CinemaRoomDTO(updatedRoom.getCinema().getId(), updatedRoom.getNumber(), updatedRoom.getCapacity());
    }

    @Override
    public List<CinemaRoomDTO> findByCinemaId(Long cinemaId) {
        return cinemaRoomRepository.findByCinemaId(cinemaId).stream()
                .map(room -> new CinemaRoomDTO(room.getCinema().getId(), room.getNumber(), room.getCapacity()))
                .toList();
    }

    @Override
    public void deleteRoom(Long roomId) {
        cinemaRoomRepository.deleteById(roomId);
    }

    @Override
    public void deleteByNumber(Long cinemaId, int roomNumber) {
        cinemaRoomRepository.findByCinemaId(cinemaId).stream().filter(room -> room.getNumber() == roomNumber)
                .findFirst()
                .ifPresentOrElse(cinemaRoomRepository::delete, () -> {
                    throw new EntityNotFoundException("Room with number " + roomNumber + " not found in cinema with id " + cinemaId);
                });
    }

    private int getSequentialRoomNumber(Cinema contextCinema) {
        List<CinemaRoom> rooms = contextCinema.getRooms();

        int roomNumber = 1;
        for (CinemaRoom room : rooms) {
            if (room.getNumber() != roomNumber) {
                return roomNumber;
            }
            roomNumber++;
        }

        return roomNumber;
    }

}
