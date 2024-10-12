package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
class ScreeningRoomService implements CinemaRoomService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Qualifier("cinemaDatabaseRepository")
    private final CinemaRepository cinemaRepository;
    private final CinemaRoomRepository cinemaRoomRepository;

    @Override
    @Transactional
    public CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation) {
        log.debug("Attempting to add room with capacity {} to cinema with id {}", roomInformation.capacity(), roomInformation.cinemaId());
        Cinema contextCinema = cinemaRepository.findById(new CinemaId(roomInformation.cinemaId()))
                .orElseThrow(() -> new EntityNotFoundException("Cinema with id " + roomInformation.cinemaId() + " not found"));
        int roomNumber = contextCinema.getNextRoomNumber();
        log.debug("Adding room with number {} to cinema with id {}", roomNumber, roomInformation.cinemaId());
        validateExistsByNumber(contextCinema, roomNumber);
        CinemaRoom room = new CinemaRoom(roomNumber, roomInformation.capacity(), contextCinema);
        CinemaRoom savedRoom = cinemaRoomRepository.save(room);
        contextCinema.addRoom(savedRoom);
        UUID cinemaId = savedRoom.getCinema().getIdValue();
        log.debug("Room with number {} added to cinema with id {}", roomNumber, cinemaId);
        return new CinemaRoomDTO(cinemaId, savedRoom.getNumber(), savedRoom.getCapacity());
    }

    @Override
    public CinemaRoomDTO updateCapacity(CinemaRoomId roomId, int newCapacity) {
        CinemaRoom room = cinemaRoomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + roomId + " not found"));
        log.debug("Updating capacity for room with id {} from {} to {}", roomId, room.getCapacity(), newCapacity);
        room.updateCapacity(newCapacity);
        CinemaRoom updatedRoom = cinemaRoomRepository.save(room);
        UUID cinemaId = updatedRoom.getCinema().getIdValue();
        log.debug("Capacity for room with id {} updated to {}", roomId, newCapacity);
        return new CinemaRoomDTO(cinemaId, updatedRoom.getNumber(), updatedRoom.getCapacity());
    }

    @Override
    public List<CinemaRoomDTO> findByCinemaId(CinemaId cinemaId) {
        return cinemaRoomRepository.findByCinemaId(cinemaId.toString()).stream()
                .map(room -> new CinemaRoomDTO(room.getCinema().getIdValue(), room.getNumber(), room.getCapacity()))
                .toList();
    }

    @Override
    public void deleteRoom(CinemaRoomId roomId) {
        log.debug("Attempting to delete room with id {}", roomId);
        cinemaRoomRepository.deleteById(roomId);
    }

    @Override
    public void deleteByNumber(CinemaId cinemaId, int roomNumber) {
        log.debug("Attempting to delete room with number: {} from cinema with id: {}", roomNumber, cinemaId);

    }

    private void validateExistsByNumber(Cinema contextCinema, int roomNumber) {
        cinemaRoomRepository.findByCinemaAndNumber(contextCinema.getIdValue().toString(), roomNumber)
                .ifPresent(room -> {
                    throw new IllegalArgumentException("Room with number " + roomNumber + " already exists in cinema with id " + contextCinema.getIdValue());
                });
    }

    private static Predicate<CinemaRoom> isSameNumber(int roomNumber) {
        return room -> room.getNumber() == roomNumber;
    }

}
