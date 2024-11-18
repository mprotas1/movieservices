package com.movieapp.screenings.domain.exception;

public class OverlappingScreeningException extends RuntimeException {

    public OverlappingScreeningException(String message) {
        super(message);
    }

}
