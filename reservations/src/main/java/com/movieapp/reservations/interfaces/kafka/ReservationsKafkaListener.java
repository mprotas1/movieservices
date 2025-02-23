package com.movieapp.reservations.interfaces.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.movieapp.reservations.application.events.PaymentStatusEvent;
import com.movieapp.reservations.application.events.ScreeningDoesNotExistEvent;
import com.movieapp.reservations.application.dto.ScreeningSeatsAlreadyLockedDTO;
import com.movieapp.reservations.application.events.SuccessfulSeatsBookingEvent;
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

    @KafkaListener(topics = "successful_seats_booking", groupId = "basic")
    void onSuccessfulSeatsBooking(String successfulSeatsBookingEvent) {
        log.debug("Successful seats booking event received: {}", successfulSeatsBookingEvent);
        reservationApplicationService.bookReservation(deserialize(successfulSeatsBookingEvent, SuccessfulSeatsBookingEvent.class));
    }

    @KafkaListener(topics = "screening_seats_already_reserved", groupId = "basic")
    void onScreeningSeatsReserved(String screeningSeatsReservedEvent) {
        log.info("Screening seats already reserved event received: {}", screeningSeatsReservedEvent);
        ScreeningSeatsAlreadyLockedDTO dto = deserialize(screeningSeatsReservedEvent, ScreeningSeatsAlreadyLockedDTO.class);
        reservationApplicationService.cancelReservation(new ReservationId(dto.reservationId()));
    }

    @KafkaListener(topics = "screening_seats_booking_failed", groupId = "basic")
    void onScreeningSeatsBookingFailed(String screeningSeatsBookingFailedEvent) {
        log.debug("Screening seats booking failed event received: {}", screeningSeatsBookingFailedEvent);
        ScreeningDoesNotExistEvent event = deserialize(screeningSeatsBookingFailedEvent, ScreeningDoesNotExistEvent.class);
        reservationApplicationService.cancelReservation(new ReservationId(event.screeningId()));
    }

    @KafkaListener(topics = "no_seats_to_book", groupId = "basic")
    void onNoSeatsToBook(String noSeatsToBookEvent) {
        log.debug("No seats to book event received: {}", noSeatsToBookEvent);
        ScreeningSeatsAlreadyLockedDTO dto = deserialize(noSeatsToBookEvent, ScreeningSeatsAlreadyLockedDTO.class);
        reservationApplicationService.cancelReservation(new ReservationId(dto.reservationId()));
    }

    @KafkaListener(topics = "payment_status", groupId = "payment-group")
    void onPaymentStatusReceived(String paymentStatusEvent) {
        PaymentStatusEvent event = deserialize(paymentStatusEvent, PaymentStatusEvent.class);
        log.debug("Payment status event received: {}", event);
        reservationApplicationService.handlePaymentStatus(event);
    }

    private <T> T deserialize(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
