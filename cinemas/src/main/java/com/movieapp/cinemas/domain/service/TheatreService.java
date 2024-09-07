package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.exception.DocumentNotFoundException;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
class TheatreService implements CinemaService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final AddressService addressService;
    private final CinemaRepository cinemaRepository;

    @Override
    @Transactional
    public CinemaDTO createCinema(@Valid CinemaInformation cinema) {
        log.debug("Creating cinema: {}", cinema);
        Address address = addressService.save(cinema.address());
        Cinema toSave = Cinema.create(cinema.name(), address);

        Cinema saved = cinemaRepository.save(toSave);
        log.debug("Saved cinema: {}", saved);

        return CinemaDTO.fromEntity(saved);
    }

    @Override
    public CinemaDTO findById(ObjectId id) {
        return cinemaRepository.findById(id)
                .map(cinema -> new CinemaDTO(cinema.getId(), cinema.getName()))
                .orElseThrow(() -> new DocumentNotFoundException("Did not find cinema with id: " + id));
    }

    @Override
    public CinemaDTO findByName(String name) {
        return cinemaRepository.findByName(name)
                .map(cinema -> new CinemaDTO(cinema.getId(), cinema.getName()))
                .orElseThrow(() -> new DocumentNotFoundException("Did not find cinema with name: " + name));
    }

    @Override
    public void deleteById(ObjectId id) {
        Cinema toBeDeleted = cinemaRepository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException("Did not find cinema with id: " + id + " to delete"));
        cinemaRepository.delete(toBeDeleted);
    }

}
