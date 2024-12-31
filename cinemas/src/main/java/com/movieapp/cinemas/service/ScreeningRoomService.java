package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.repository.CinemaRoomRepository;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import com.movieapp.cinemas.service.model.SeatDTO;
import jakarta.persistence.EntityExistsException;
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
    private final SeatsMapper seatsMapper;

    @Override
    public CinemaRoomDTO findById(CinemaRoomId id) {
        return cinemaRoomRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Room with id " + id + " not found"));
    }

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
        UUID cinemaId = savedRoom.getCinemaId().getUuid();
        log.debug("Room with number {} added to cinema with id {}", roomNumber, cinemaId);
        return toDTO(savedRoom);
    }

    @Override
    public CinemaRoomDTO updateCapacity(CinemaId cinemaId, int roomNumber, int newCapacity) {
        CinemaRoom room = cinemaRoomRepository.findByCinemaAndNumber(cinemaId, roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Room with number: " + roomNumber + " not found"));
        log.debug("Updating capacity for room with id {} from {} to {}", room.getId().getValue(), room.getCapacity(), newCapacity);
        room.updateCapacity(newCapacity);
        CinemaRoom updatedRoom = cinemaRoomRepository.save(room);
        log.debug("Capacity for room with id {} updated to {}", room.getId().getValue(), newCapacity);
        return toDTO(updatedRoom);
    }

    @Override
    public CinemaRoomDTO findByCinemaAndNumber(CinemaId cinemaId, int roomNumber) {
        return cinemaRoomRepository.findByCinemaAndNumber(cinemaId, roomNumber)
                .map(this::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Room with number: " + roomNumber + " not found"));
    }

    @Override
    public List<CinemaRoomDTO> findByCinemaId(CinemaId cinemaId) {
        return cinemaRoomRepository.findByCinemaId(cinemaId).stream()
                .map(this::toDTO)
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
        CinemaRoom room = cinemaRoomRepository.findByCinemaAndNumber(cinemaId, roomNumber)
                .orElseThrow(() -> new EntityNotFoundException("Room with number: " + roomNumber + " not found"));
        cinemaRoomRepository.delete(room);
    }

    private void validateExistsByNumber(Cinema contextCinema, int roomNumber) {
        cinemaRoomRepository.findByCinemaAndNumber(contextCinema.getId(), roomNumber)
                .ifPresent(room -> {
                    throw new EntityExistsException("Room with number " + roomNumber + " already exists in cinema with id " + contextCinema.getIdValue());
                });
    }

    private static Predicate<CinemaRoom> isSameNumber(int roomNumber) {
        return room -> room.getNumber() == roomNumber;
    }

    private CinemaRoomDTO toDTO(CinemaRoom room) {
        return new CinemaRoomDTO(
                room.getId().getValue(),
                room.getCinemaId().getUuid(),
                room.getNumber(),
                room.getCapacity(),
                getMappedSeats(room)
        );
    }

    private List<SeatDTO> getMappedSeats(CinemaRoom room) {
        return room.getSeats().stream()
                .map(seatsMapper::toDTO)
                .toList();
    }

}
