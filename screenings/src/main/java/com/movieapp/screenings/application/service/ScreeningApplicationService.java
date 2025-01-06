package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.ReservationDTO;
import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;

import java.util.List;
import java.util.UUID;

public interface ScreeningApplicationService {
    ScreeningDTO createScreening(ScreeningCreateRequest request);
    List<ScreeningDTO> findAll();
    ScreeningDTO findById(UUID screeningId);
    void lockSeats(ReservationDTO reservationDTO);
}
