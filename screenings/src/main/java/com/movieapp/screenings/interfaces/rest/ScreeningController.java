package com.movieapp.screenings.interfaces.rest;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.application.service.ScreeningApplicationService;
import com.movieapp.screenings.domain.model.ScreeningId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
class ScreeningController {
    private final ScreeningApplicationService screeningService;

    @PostMapping
    ResponseEntity<ScreeningDTO> createScreening(@RequestBody ScreeningCreateRequest request) {
        log.debug("Creating new Screening with request: {}", request);
        var screening = screeningService.createScreening(request);
        log.debug("Created Screening: {}", screening);
        return ResponseEntity.created(getScreeningLocation(screening)).body(screening);
    }

    @GetMapping
    ResponseEntity<List<ScreeningDTO>> findAll() {
        var screenings = screeningService.findAll();
        log.debug("Found {} Screenings", screenings.size());
        return ResponseEntity.ok(screenings);
    }

    @GetMapping("/{id}")
    ResponseEntity<ScreeningDTO> findById(@PathVariable String id) {
        var screening = screeningService.findById(UUID.fromString(id));
        log.debug("Found Screening: {}", screening);
        return ResponseEntity.ok(screening);
    }

    @GetMapping("/byMovieAndCinema")
    ResponseEntity<List<ScreeningDTO>> findByMovieAndCinema(@RequestParam Long movieId, @RequestParam String cinemaId) {
        var screenings = screeningService.findByMovieAndCinemaId(movieId, UUID.fromString(cinemaId));
        return ResponseEntity.ok(screenings);
    }

    private URI getScreeningLocation(ScreeningDTO screening) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{reservationId}")
                .buildAndExpand(screening.screeningId())
                .toUri();
    }

}
