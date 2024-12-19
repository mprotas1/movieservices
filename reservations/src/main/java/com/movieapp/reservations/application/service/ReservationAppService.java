package com.movieapp.reservations.application.service;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import com.movieapp.reservations.domain.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class ReservationAppService implements ReservationApplicationService {
    private final ReservationDomainService reservationDomainService;
    private final ReservationRepository reservationRepository;

    @Override
    public ReservationDTO makeReservation(ReservationCreateRequest request) {
        log.debug("Making reservation for request: {}", request);
        return null;
    }

    @Override
    public ReservationDTO cancelReservation(ReservationId reservationDTO) {
        log.debug("Cancelling reservation with id: {}", reservationDTO.getId());

        return null;
    }

    @Override
    public ReservationDTO findById(ReservationId reservationId) {
        return reservationRepository.findById(reservationId)
                .map(ReservationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with id: " + reservationId.getId() + " not found"));
    }

    @Override
    public ReservationDTO findByScreeningIdAndSeatId(ScreeningId screeningId, SeatId seatId) {
        return reservationRepository.findByScreeningIdAndSeatId(screeningId, seatId)
                .map(ReservationMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Reservation with screeningId: " + screeningId.id() + " and seatId: " + seatId.id() + " not found"));
    }

    @Override
    public List<ReservationDTO> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findAllByScreeningId(ScreeningId screeningId) {
        return reservationRepository.findByScreeningId(screeningId).stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    public List<ReservationDTO> findUserReservations(UserId userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(ReservationMapper::toDTO)
                .toList();
    }

    @Override
    public void deleteById(ReservationId reservationId) {
        log.debug("Deleting reservation with id: {}", reservationId.getId());
        reservationRepository.deleteById(reservationId);
    }
}
