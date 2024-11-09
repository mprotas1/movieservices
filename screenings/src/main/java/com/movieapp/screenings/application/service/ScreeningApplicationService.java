package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.ScreeningCreateRequest;
import com.movieapp.screenings.application.dto.ScreeningDTO;

public interface ScreeningApplicationService {
    ScreeningDTO createScreening(ScreeningCreateRequest request);
}
