package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.model.CinemaRoomDTO;
import com.movieapp.cinemas.domain.model.CinemaRoomInformation;
import com.movieapp.cinemas.domain.service.CinemaService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{cinemaId}/rooms")
@AllArgsConstructor
@Slf4j
public class CinemaRoomsController {
    private final CinemaService cinemaService;

    @PostMapping
    ResponseEntity<CinemaRoomDTO> addRoom(@PathVariable Long cinemaId, @RequestParam int capacity) {
        CinemaRoomDTO dto = cinemaService.addRoom(new CinemaRoomInformation(cinemaId, capacity));
        return ResponseEntity.ok(dto);
    }

}
