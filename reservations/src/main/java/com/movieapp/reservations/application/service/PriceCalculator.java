package com.movieapp.reservations.application.service;

import com.movieapp.reservations.application.dto.PricedSeatDTO;

import java.util.List;

public interface PriceCalculator {
    Double calculatePrice(List<PricedSeatDTO> seats);
}
