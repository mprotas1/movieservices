package com.movieapp.screenings.interfaces.rest;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.application.service.ScreeningApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        return ResponseEntity.ok(screening);
    }

    @GetMapping
    ResponseEntity<List<ScreeningDTO>> findAll() {
        var screenings = screeningService.findAll();
        log.debug("Found {} Screenings", screenings.size());
        return ResponseEntity.ok(screenings);
    }

}
