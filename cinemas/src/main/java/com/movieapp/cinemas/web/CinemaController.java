package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.service.model.CinemaDTO;
import com.movieapp.cinemas.service.model.CinemaInformation;
import com.movieapp.cinemas.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/{id}")
    public ResponseEntity<CinemaDTO> findById(@PathVariable String id) {
        CinemaId cinemaId = new CinemaId(UUID.fromString(id));
        CinemaDTO dto = cinemaService.findById(cinemaId);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<List<CinemaDTO>> findAll(@PageableDefault(size = Integer.MAX_VALUE, page = 0) Pageable pageable) {
        return ResponseEntity.ok(cinemaService.findAll(pageable.getPageNumber(), pageable.getPageSize()));
    }
    @GetMapping("/search")
    public ResponseEntity<CinemaDTO> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(cinemaService.findByName(name));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        CinemaId cinemaId = new CinemaId(UUID.fromString(id));
        cinemaService.deleteById(cinemaId);
        return ResponseEntity.noContent().build();
    }

    private URI getResponseURI(CinemaDTO cinemaDTO) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cinemaDTO.id())
                .toUri();
    }

}
