package com.movieapp.reservations.interfaces.kafka;

import com.movieapp.reservations.application.dto.ReservationDTO;

public interface KafkaProducer {
    void reservationCreated(ReservationDTO reservationDTO);
}
