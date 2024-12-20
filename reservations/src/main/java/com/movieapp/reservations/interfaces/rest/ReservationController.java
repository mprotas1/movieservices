package com.movieapp.reservations.interfaces.rest;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.application.service.ReservationApplicationService;
import com.movieapp.reservations.domain.ReservationDomainService;
import com.movieapp.reservations.domain.ReservationId;
import com.movieapp.reservations.domain.ScreeningId;
import com.movieapp.reservations.domain.UserId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
class ReservationController {
    private final ReservationApplicationService applicationService;

    @PostMapping("/reserve")
    ResponseEntity<ReservationDTO> reserve(@RequestBody ReservationCreateRequest request) {
        log.debug("Received reservation request: {}", request);
        ReservationDTO reservationDTO = applicationService.makeReservation(request);
        log.debug("Reservation created: {}", reservationDTO);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping("/{id}")
    ResponseEntity<ReservationDTO> findById(@PathVariable UUID id) {
        log.debug("Searching for reservation with id: {}", id);
        ReservationDTO reservationDTO = applicationService.findById(new ReservationId(id));
        log.debug("Reservation found: {}", reservationDTO);
        return ResponseEntity.ok(reservationDTO);
    }

    @GetMapping
    ResponseEntity<List<ReservationDTO>> findAll() {
        log.debug("Searching for all reservations");
        List<ReservationDTO> reservationDTOs = applicationService.findAll();
        log.debug("Reservations found: {}", reservationDTOs);
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/byUser/{userId}")
    ResponseEntity<List<ReservationDTO>> findUserReservations(@PathVariable Long userId) {
        log.debug("Searching for all reservations of user with id: {}", userId);
        List<ReservationDTO> reservationDTOs = applicationService.findUserReservations(new UserId(userId));
        return ResponseEntity.ok(reservationDTOs);
    }

    @GetMapping("/byScreening/{screeningId}")
    ResponseEntity<List<ReservationDTO>> findByScreeningId(@PathVariable UUID screeningId) {
        log.debug("Searching for all reservations of screening with id: {}", screeningId);
        List<ReservationDTO> reservationDTOs = applicationService.findAllByScreeningId(new ScreeningId(screeningId));
        return ResponseEntity.ok(reservationDTOs);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteById(@PathVariable UUID id) {
        log.debug("Deleting reservation with id: {}", id);
        applicationService.deleteById(new ReservationId(id));
        log.debug("Reservation deleted");
        return ResponseEntity.noContent().build();
    }

}
