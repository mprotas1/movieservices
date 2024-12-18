package com.movieapp.reservations.application.service;

import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.domain.ReservationId;
import com.movieapp.reservations.domain.ScreeningId;
import com.movieapp.reservations.domain.SeatId;
import com.movieapp.reservations.domain.UserId;

import java.util.List;

public interface ReservationApplicationService {
    ReservationDTO makeReservation(ReservationDTO reservationDTO);
    ReservationDTO cancelReservation(ReservationId reservationDTO);
    ReservationDTO findById(ReservationId reservationId);
    ReservationDTO findByScreeningIdAndSeatId(ScreeningId screeningId, SeatId seatId);
    List<ReservationDTO> findAllByScreeningId(ScreeningId screeningId);
    List<ReservationDTO> findUserReservations(UserId userId);
    void deleteById(ReservationId reservationId);
}
