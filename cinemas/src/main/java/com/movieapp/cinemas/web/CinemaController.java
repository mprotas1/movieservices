package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.model.CinemaDTO;
import com.movieapp.cinemas.domain.model.CinemaInformation;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class CinemaController {
    private final CinemaService cinemaService;

    @PostMapping
    public ResponseEntity<CinemaDTO> create(@RequestBody CinemaInformation cinemaInformation) {
        CinemaDTO cinema = cinemaService.createCinema(cinemaInformation);
        return ResponseEntity.created(getResponseURI(cinema)).body(cinema);
    }

    @PostMapping
    public ResponseEntity<?> addRoom(@RequestBody CinemaRoomInformation cinemaRoomInformation) {
        cinemaService.addRoom(cinemaRoomInformation);
        return ResponseEntity.noContent().build();
    }

    private URI getResponseURI(CinemaDTO cinemaDTO) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cinemaDTO.id()).toUri();
    }

}
