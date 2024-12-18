package com.movieapp.reservations.domain;

import com.movieapp.reservations.application.dto.ReservationCreateRequest;

public interface ReservationDomainService {
    Reservation makeReservation(ReservationCreateRequest request);
}
