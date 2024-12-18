package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class ReservationService implements ReservationDomainService {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation makeReservation(ReservationCreateRequest reservationDTO) {
        log.debug("Making reservation: {}", reservationDTO);
        validateScreening(reservationDTO.screeningId());
        validateSeat(reservationDTO.seatId());
        validateUser(reservationDTO.userId());
        return ReservationMapper.toDomain(reservationDTO);
    }

    private void validateScreening(UUID screeningId) {
        // check if Screening exists by REST API call
    }

    private void validateSeat(UUID seatId) {
        // check if Seat exists by REST API call and if it is available
    }

    private void validateUser(Long userId) {
        // check if User exists by REST API call
    }

}
