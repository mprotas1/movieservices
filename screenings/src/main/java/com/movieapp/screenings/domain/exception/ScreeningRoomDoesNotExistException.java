package com.movieapp.screenings.domain.exception;

public class ScreeningRoomDoesNotExistException extends RuntimeException {

    public ScreeningRoomDoesNotExistException(String message) {
        super(message);
    }

}
