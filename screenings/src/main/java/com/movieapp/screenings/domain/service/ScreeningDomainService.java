package com.movieapp.screenings.domain.service;

import com.movieapp.screenings.domain.exception.ScreeningSeatsAlreadyBookedException;
import com.movieapp.screenings.domain.exception.ScreeningSeatsBookingException;
import com.movieapp.screenings.domain.model.Screening;
import com.movieapp.screenings.domain.model.ScreeningSeat;
import com.movieapp.screenings.domain.model.SeatId;

import java.util.List;
import java.util.Set;

public interface ScreeningDomainService {
    Screening createScreening(Screening screening);
    Set<ScreeningSeat> lockSeats(Screening screening, List<SeatId> ids) throws ScreeningSeatsAlreadyBookedException, ScreeningSeatsBookingException;
}
