package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.service.CinemaRoomService;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import com.movieapp.cinemas.service.model.CinemaRoomInformation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/{cinemaId}/rooms")
@AllArgsConstructor
@Slf4j
class CinemaRoomsController {
    private final CinemaRoomService cinemaRoomService;

    @GetMapping
    ResponseEntity<List<CinemaRoomDTO>> findByCinemaId(@PathVariable String cinemaId) {
        log.debug("Finding rooms for cinema with id: {}", cinemaId);
        CinemaId id = new CinemaId(UUID.fromString(cinemaId));
        return ResponseEntity.ok(cinemaRoomService.findByCinemaId(id));
    }

    @GetMapping("/{roomNumber}")
    ResponseEntity<CinemaRoomDTO> findByRoomNumber(@PathVariable String cinemaId, @PathVariable int roomNumber) {
        log.debug("Finding room with number {} for cinema with id: {}", roomNumber, cinemaId);
        CinemaId id = new CinemaId(UUID.fromString(cinemaId));
        return ResponseEntity.ok(cinemaRoomService.findByCinemaAndNumber(id, roomNumber));
    }

    @PostMapping
    ResponseEntity<CinemaRoomDTO> addRoom(@PathVariable String cinemaId, @RequestParam int capacity) {
        log.debug("Adding room to cinema with id: {}", cinemaId);
        CinemaRoomDTO dto = cinemaRoomService.addRoom(new CinemaRoomInformation(UUID.fromString(cinemaId), capacity));
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{roomNumber}")
    ResponseEntity<Void> deleteByRoomNumber(@PathVariable String cinemaId, @PathVariable int roomNumber) {
        log.debug("Deleting room with number {} from cinema with id {}", roomNumber, cinemaId);
        CinemaId id = new CinemaId(UUID.fromString(cinemaId));
        cinemaRoomService.deleteByNumber(id, roomNumber);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{roomNumber}")
    ResponseEntity<CinemaRoomDTO> updateRoomCapacity(@PathVariable String cinemaId,
                                                     @PathVariable int roomNumber,
                                                     @RequestParam int newCapacity) {
        log.debug("Updating room with number {} from cinema with id {}", roomNumber, cinemaId);
        CinemaId id = new CinemaId(UUID.fromString(cinemaId));
        CinemaRoomDTO dto = cinemaRoomService.updateCapacity(id, roomNumber, newCapacity);
        return ResponseEntity.ok(dto);
    }

}
