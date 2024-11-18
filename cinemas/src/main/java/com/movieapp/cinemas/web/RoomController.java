package com.movieapp.cinemas.web;

import com.movieapp.cinemas.domain.entity.CinemaRoomId;
import com.movieapp.cinemas.service.CinemaRoomService;
import com.movieapp.cinemas.service.model.CinemaRoomDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
class RoomController {
    private final CinemaRoomService roomService;

    @GetMapping("/{id}")
    ResponseEntity<CinemaRoomDTO> findById(@PathVariable UUID id) {
        CinemaRoomId roomId = new CinemaRoomId(id);
        CinemaRoomDTO byId = roomService.findById(roomId);
        return ResponseEntity.ok(byId);
    }

}
