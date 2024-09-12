package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
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
        return cinemaRepository.findById(id)
                .map(cinema -> new CinemaDTO(cinema.getId(), cinema.getName()))
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with id: " + id));
    }

    @Override
    public CinemaDTO findByName(String name) {
        return cinemaRepository.findByName(name)
                .map(cinema -> new CinemaDTO(cinema.getId(), cinema.getName()))
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with name: " + name));
    }

    @Override
    public void deleteById(Long id) {
        Cinema toBeDeleted = cinemaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Did not find cinema with id: " + id + " to delete"));
        cinemaRepository.delete(toBeDeleted);
        log.debug("Deleted cinema: {}", toBeDeleted);
    }

    private void validateCinemaExistsByName(String name) {
        cinemaRepository.findByName(name).ifPresent(cinema -> {
            throw new EntityExistsException("Cinema with name: " + name + " already exists");
        });
    }

}
