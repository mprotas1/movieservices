package com.movieapp.cinemas.service;

import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.Coordinates;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.infrastructure.location.CinemaLocationService;
import com.movieapp.cinemas.service.model.AddressInformation;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Validated
class TheatreService implements CinemaService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    @Qualifier("googleCinemaLocationService")
    private final CinemaLocationService locationService;
    private final CinemaRepository cinemaRepository;

    @Override
    public List<CinemaDTO> findAll() {
        return cinemaRepository.findAll()
                .stream()
                .map(CinemaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<CinemaDTO> findAll(int page, int size) {
        return cinemaRepository.findAll(page, size)
                .stream()
                .map(CinemaDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public CinemaDTO createCinema(@Valid CinemaInformation cinema) {
        log.debug("Creating cinema: {}", cinema);
        validateCinemaExistsByName(cinema.name());
        AddressInformation address = cinema.address();
        Coordinates coordinates = locationService.getCoordinates(address.city(), address.street(), address.postalCode());
        Cinema toSave = new Cinema(cinema.name(), AddressInformation.toEntity(cinema.address()), coordinates);
        Cinema saved = cinemaRepository.save(toSave);
        log.debug("Saved cinema: {}", saved);
        return CinemaDTO.fromEntity(saved);
    }

    @Override
    public CinemaDTO findById(CinemaId cinemaId) {
        log.debug("Finding cinema with id: {}", cinemaId);
        UUID id = cinemaId.getUuid();
        return cinemaRepository.findById(cinemaId)
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
    public void deleteById(CinemaId id) {
        log.debug("Deleting cinema with id: {}", id);
        Cinema toBeDeleted = cinemaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with id: " + id + " to delete"));
        log.debug("Attempting to delete cinema: {} - {}", toBeDeleted.getId(), toBeDeleted.getName());
        cinemaRepository.deleteById(id);
        log.debug("Deleted cinema: {}", toBeDeleted);
    }

    private void validateCinemaExistsByName(String name) {
        cinemaRepository.findByName(name).ifPresent(cinema -> {
            throw new EntityExistsException("Cinema with name: " + name + " already exists");
        });
    }

}
