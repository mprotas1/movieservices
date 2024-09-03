package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;
import com.movieapp.cinemas.domain.exception.DocumentNotFoundException;
import com.movieapp.cinemas.domain.repository.CinemaRepository;
import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class TheatreService implements CinemaService {
    private final AddressService addressService;
    private final CinemaRepository cinemaRepository;

    @Override
    public CinemaDTO createCinema(CinemaInformation cinema) {
        // 1. Create the Cinema object with the CinemaInformation object
        Address address = addressService.save(cinema.address());
        Cinema toSave = Cinema.create(cinema.name(), address);

        // 2. Save the Cinema object using the cinemaRepository
        Cinema saved = cinemaRepository.save(toSave);

        // 3. Return the CinemaDTO object created from the saved Cinema object
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
