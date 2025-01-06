package com.movieapp.screenings.interfaces.client;

import com.movieapp.screenings.application.dto.PricedSeatDTO;
import com.movieapp.screenings.application.dto.ScreeningDTO;

import java.util.List;

public interface PricingClient {
    List<PricedSeatDTO> getSeatsPricing(ScreeningDTO screeningDTO);
}
