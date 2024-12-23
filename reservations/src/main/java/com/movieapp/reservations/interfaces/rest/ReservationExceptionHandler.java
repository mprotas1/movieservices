package com.movieapp.reservations.interfaces.rest;

import com.movieapp.reservations.domain.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ReservationExceptionHandler {

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            ScreeningNotFoundException.class,
            SeatNotFoundException.class,
            UserNotFoundException.class
    })
    ResponseEntity<ProblemDetail> handleNotFoundException(RuntimeException runtimeException) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, runtimeException.getMessage()))
                .build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    ResponseEntity<ProblemDetail> handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, illegalArgumentException.getMessage()))
                .build();
    }

    @ExceptionHandler(value = {
            SeatIsAlreadyTakenException.class,
            InvalidReservationTransitionException.class
    })
    ResponseEntity<ProblemDetail> handleSeatIsAlreadyTakenException(SeatIsAlreadyTakenException seatIsAlreadyTakenException) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, seatIsAlreadyTakenException.getMessage()))
                .build();
    }

}
