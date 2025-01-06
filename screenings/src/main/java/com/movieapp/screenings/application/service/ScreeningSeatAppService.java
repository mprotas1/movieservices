package com.movieapp.screenings.application.service;

import com.movieapp.screenings.application.dto.PricedSeatDTO;
import com.movieapp.screenings.application.dto.ScreeningDTO;
import com.movieapp.screenings.application.dto.ScreeningSeatDTO;
import com.movieapp.screenings.application.mapper.ScreeningMapper;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningSeat;
import com.movieapp.screenings.domain.model.ScreeningSeats;
import com.movieapp.screenings.domain.repository.ScreeningRepository;
import com.movieapp.screenings.interfaces.client.PricingClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@AllArgsConstructor
@Slf4j
class ScreeningSeatAppService implements ScreeningSeatService {
    private final PricingClient pricingClient;
    private final ScreeningMapper screeningMapper;
    private final ScreeningRepository screeningRepository;

    @Override
    public Screening priceSeats(ScreeningDTO screeningDTO) {
        var pricedSeats = pricingClient.getSeatsPricing(screeningDTO);

        Screening domainScreening = screeningMapper.toDomain(screeningDTO);
        ScreeningSeats domainSeats = domainScreening.getSeats();

        for(ScreeningSeat screeningSeat : domainSeats.screeningSeats()) {
            Optional<PricedSeatDTO> pricedSeatDTO = pricedSeats.stream()
                    .filter(pricedSeat -> pricedSeat.id().equals(screeningSeat.getSeatId().id()))
                    .findFirst();

            if(pricedSeatDTO.isEmpty()) {
                log.error("Seat with reservationId {} not found in priced seats", screeningSeat.getSeatId().id());
                continue;
            }

            PricedSeatDTO pricedSeat = pricedSeatDTO.get();
            screeningSeat.setPrice(pricedSeat.price());
        }


        return screeningRepository.save(domainScreening);
    }

}