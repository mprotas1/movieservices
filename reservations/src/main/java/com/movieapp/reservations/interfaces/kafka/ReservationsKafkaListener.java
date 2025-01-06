package com.movieapp.reservations.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.reservations.application.dto.ScreeningSeatsAlreadyLockedDTO;
import com.movieapp.reservations.application.service.ReservationApplicationService;
import com.movieapp.reservations.domain.ReservationId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
class ReservationsKafkaListener {
    private final ReservationApplicationService reservationApplicationService;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "screening_seats_already_reserved", groupId = "basic")
    public void onScreeningSeatsReserved(String screeningSeatsReservedEvent) {
        reservationApplicationService.cancelReservation(new ReservationId(getReservationIdFromEvent(screeningSeatsReservedEvent)));
    }

    @KafkaListener(topics = "successful_seats_booking", groupId = "basic")
    public void onSuccessfulSeatsBooking(String successfulSeatsBookingEvent) {
        // next is Payment service to be called with totalAmount for the reservation
        log.debug("Successful seats booking event received: {}", successfulSeatsBookingEvent);
    }

    private UUID getReservationIdFromEvent(String eventJson) {
        ScreeningSeatsAlreadyLockedDTO dto = null;
        try {
            dto = objectMapper.readValue(eventJson, ScreeningSeatsAlreadyLockedDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return dto.reservationId();
    }

}
