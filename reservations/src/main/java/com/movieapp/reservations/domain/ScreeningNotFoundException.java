package com.movieapp.reservations.domain;

public class ScreeningNotFoundException extends RuntimeException {

        public ScreeningNotFoundException(String message) {
            super(message);
        }

}
