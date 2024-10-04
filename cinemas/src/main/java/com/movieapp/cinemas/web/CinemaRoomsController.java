package com.movieapp.cinemas.web;

import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import com.movieapp.cinemas.service.CinemaRoomService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{cinemaId}/rooms")
@AllArgsConstructor
@Slf4j
public class CinemaRoomsController {
    private final CinemaRoomService cinemaRoomService;

    @PostMapping
    ResponseEntity<CinemaRoomDTO> addRoom(@PathVariable Long cinemaId, @RequestParam int capacity) {
        log.debug("Adding room to cinema with id: {}", cinemaId);
        CinemaRoomDTO dto = cinemaRoomService.addRoom(new CinemaRoomInformation(cinemaId, capacity));
        return ResponseEntity.ok(dto);
    }

}
