package com.movieapp.reservations.interfaces.kafka;

import com.movieapp.reservations.application.events.ReservationCreatedEvent;
import com.movieapp.reservations.application.events.ReservationPaymentEvent;

public interface KafkaProducer {
    void reservationCreated(ReservationCreatedEvent event);
    void askForPayment(ReservationPaymentEvent event);
}
