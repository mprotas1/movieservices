package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.application.dto.ReservationDTO;
import com.movieapp.screenings.domain.model.Screening;

public interface ScreeningDomainService {
    Screening createScreening(Screening screening);
    void lockSeats(ReservationDTO reservationDTO);
}
