package com.movieapp.reservations.domain;

public class InvalidReservationTransitionException extends RuntimeException {

    public InvalidReservationTransitionException(String message) {
            super(message);
        }

}
