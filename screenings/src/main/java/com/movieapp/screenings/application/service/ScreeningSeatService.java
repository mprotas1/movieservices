package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.domain.model.Screening;

public interface ScreeningSeatService {
    Screening priceSeats(ScreeningDTO screeningDTO);
}
