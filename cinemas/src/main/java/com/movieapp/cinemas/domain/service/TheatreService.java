package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.domain.model.CinemaRoomDTO;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
class TheatreService implements CinemaService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final AddressService addressService;
    private final CinemaRepository cinemaRepository;

    @Override
    @Transactional
    public CinemaDTO createCinema(@Valid CinemaInformation cinema) {
        log.debug("Creating cinema: {}", cinema);
        validateCinemaExistsByName(cinema.name());
        Address address = addressService.save(cinema.address());
        Cinema toSave = Cinema.create(cinema.name(), address);
        Cinema saved = cinemaRepository.save(toSave);
        log.debug("Saved cinema: {}", saved);
        return CinemaDTO.fromEntity(saved);
    }

    @Override
    public CinemaDTO findById(Long id) {
        log.debug("Finding cinema with id: {}", id);
        return cinemaRepository.findById(id)
                .map(CinemaDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with id: " + id));
    }

    @Override
    public CinemaDTO findByName(String name) {
        log.debug("Finding cinema with name: {}", name);
        return cinemaRepository.findByName(name)
                .map(CinemaDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with name: " + name));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        log.debug("Deleting cinema with id: {}", id);
        Cinema toBeDeleted = cinemaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with id: " + id + " to delete"));
        log.debug("Attempting to delete cinema: {} - {}", toBeDeleted.getId(), toBeDeleted.getName());
        cinemaRepository.delete(toBeDeleted);
        log.debug("Deleted cinema: {}", toBeDeleted);
    }

    @Override
    @Transactional
    public CinemaRoomDTO addRoom(CinemaRoomInformation roomInformation) {
        log.debug("Adding room with capacity: {} to cinema with id: {}", roomInformation.capacity(), roomInformation.cinemaId());
        Cinema cinema = cinemaRepository.findById(roomInformation.cinemaId())
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with id: " + roomInformation.cinemaId()));
        int roomNumber = getRoomNumberForCinema(cinema);
        CinemaRoom cinemaRoom = CinemaRoom.roomInCinema(cinema, roomInformation.capacity(), roomNumber);
        log.debug("Created CinemaRoom: {}", cinemaRoom);
        Cinema cinemaWithRoom = cinemaRepository.save(cinema);
        log.debug("Saved Cinema: [{} - {}] with room: {}, currently has {} rooms",
                cinemaWithRoom.getId(), cinemaWithRoom.getName(), cinemaRoom.getId(), cinemaWithRoom.getRooms().size());
        return new CinemaRoomDTO(cinemaWithRoom.getId(), roomNumber, cinemaRoom.getCapacity());
    }

    private int getRoomNumberForCinema(Cinema cinema) {
        int numberOfRooms = cinema.getRooms().size();
        return ++numberOfRooms;
    }

    private void validateCinemaExistsByName(String name) {
        cinemaRepository.findByName(name).ifPresent(_ -> {
            throw new EntityExistsException("Cinema with name: " + name + " already exists");
        });
    }

}
