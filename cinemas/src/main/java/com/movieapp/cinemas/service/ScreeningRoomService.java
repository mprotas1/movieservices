package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
class ScreeningRoomService implements CinemaRoomService {
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    @Override
    @Transactional
    public CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation) {
        Cinema contextCinema = cinemaRepository.findById(new CinemaId())
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + roomInformation.cinemaId() + " not found"));
        int roomNumber = contextCinema.getNextRoomNumber();
        CinemaRoom room = new CinemaRoom(roomNumber, roomInformation.capacity(), contextCinema);
        CinemaRoom savedRoom = cinemaRoomRepository.save(room);
        UUID cinemaId = savedRoom.getCinema().getIdValue();
        return new CinemaRoomDTO(cinemaId, savedRoom.getNumber(), savedRoom.getCapacity());
    }

    @Override
    public CinemaRoomDTO updateCapacity(Long roomId, int newCapacity) {
        CinemaRoom room = cinemaRoomRepository.findById(new CinemaRoomId(roomId).getValue())
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + roomId + " not found"));
        room.updateCapacity(newCapacity);
        CinemaRoom updatedRoom = cinemaRoomRepository.save(room);
        UUID cinemaId = updatedRoom.getCinema().getIdValue();
        return new CinemaRoomDTO(cinemaId, updatedRoom.getNumber(), updatedRoom.getCapacity());
    }

    @Override
    public List<CinemaRoomDTO> findByCinemaId(UUID cinemaId) {

        return cinemaRoomRepository.findByCinemaId(cinemaId.toString()).stream()
                .map(room -> new CinemaRoomDTO(room.getCinema().getIdValue(), room.getNumber(), room.getCapacity()))
                .toList();
    }

    @Override
    public void deleteRoom(Long roomId) {
        cinemaRoomRepository.deleteById(roomId);
    }

    @Override
    public void deleteByNumber(UUID cinemaId, int roomNumber) {
        cinemaRoomRepository.findByCinemaId(cinemaId.toString()).stream()
                .filter(isSameNumber(roomNumber))
                .findFirst()
                .ifPresentOrElse(cinemaRoomRepository::delete, () -> {
                    throw new EntityNotFoundException("Room with number " + roomNumber + " not found in cinema with id " + cinemaId);
                });
    }

    private static Predicate<CinemaRoom> isSameNumber(int roomNumber) {
        return room -> room.getNumber() == roomNumber;
    }

}
