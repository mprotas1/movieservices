package com.movieapp.reservations.application.service;

import com.movieapp.reservations.application.dto.PricedSeatDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ReservationPriceCalculator implements PriceCalculator {

    @Override
    public Double calculatePrice(List<PricedSeatDTO> seats) {
        return seats.stream()
                .map(PricedSeatDTO::price)
                .reduce(0.0, Double::sum);
    }

}
