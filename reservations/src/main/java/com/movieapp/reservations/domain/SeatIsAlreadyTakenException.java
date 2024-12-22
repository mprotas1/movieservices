package com.movieapp.reservations.domain;

public class SeatIsAlreadyTakenException extends RuntimeException {

    public SeatIsAlreadyTakenException(String message) {
        super(message);
    }

}
