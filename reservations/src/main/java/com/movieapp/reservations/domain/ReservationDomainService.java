package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;

public interface ReservationDomainService {
    Reservation makeReservation(ReservationCreateRequest request);
    Reservation confirmReservation(ReservationId reservationId);
    Reservation cancelReservation(ReservationId reservationId);
    Reservation bookReservation(ReservationId reservationId, ReservationPrice price);
}
