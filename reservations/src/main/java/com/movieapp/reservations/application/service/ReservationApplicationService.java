package com.movieapp.reservations.application.service;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;
import com.movieapp.reservations.application.dto.ReservationDTO;
import com.movieapp.reservations.domain.ReservationId;
import com.movieapp.reservations.domain.ScreeningId;
import com.movieapp.reservations.domain.SeatId;
import com.movieapp.reservations.domain.UserId;

import java.util.List;

public interface ReservationApplicationService {
    ReservationDTO makeReservation(ReservationCreateRequest reservationDTO);
    ReservationDTO confirmReservation(ReservationId reservationId);
    ReservationDTO cancelReservation(ReservationId reservationId);
    ReservationDTO findById(ReservationId reservationId);
    List<ReservationDTO> findAll();
    List<ReservationDTO> findAllByScreeningId(ScreeningId screeningId);
    List<ReservationDTO> findUserReservations(UserId userId);
    void deleteById(ReservationId reservationId);
}
