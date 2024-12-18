package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.mapper.ReservationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
class ReservationService implements ReservationDomainService {
    private final ReservationRepository reservationRepository;

    @Override
    public Reservation makeReservation(ReservationCreateRequest reservationDTO) {
        log.debug("Making reservation: {}", reservationDTO);
        validateScreening(reservationDTO.getScreeningId());
        validateSeat(reservationDTO.getSeatId());
        validateUser(reservationDTO.getUserId());
        Reservation reservation = ReservationMapper.
        return null;
    }

    private void validateScreening(ScreeningId screeningId) {
        // check if Screening exists by REST API call
    }

    private void validateSeat(SeatId seatId) {
        // check if Seat exists by REST API call and if it is available
    }

    private void validateUser(UserId userId) {
        // check if User exists by REST API call
    }

}
