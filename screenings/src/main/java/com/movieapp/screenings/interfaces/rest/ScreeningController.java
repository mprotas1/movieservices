package com.movieapp.screenings.interfaces.rest;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.application.service.ScreeningApplicationService;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.respository.ScreeningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
class ScreeningController {
    private final ScreeningApplicationService screeningService;
    private final ScreeningRepository screeningRepository;

    @PostMapping
    ResponseEntity<ScreeningDTO> createScreening(@RequestBody ScreeningCreateRequest request) {
        return ResponseEntity.ok(new ScreeningDTO(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID(), request.startTime(), request.startTime().plusSeconds(request.duration())));
    }

    @GetMapping
    ResponseEntity<List<Screening>> findAll() {
        List<Screening> screenings = screeningRepository.findAll();
        return ResponseEntity.ok(screenings);
    }

}
