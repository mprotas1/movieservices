package com.movieapp.reservations.domain;

public class SeatNotFoundException extends RuntimeException {

        public SeatNotFoundException(String message) {
            super(message);
        }

}