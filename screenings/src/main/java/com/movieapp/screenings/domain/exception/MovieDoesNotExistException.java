package com.movieapp.screenings.domain.exception;

public class MovieDoesNotExistException extends RuntimeException {

    public MovieDoesNotExistException(String message) {
        super(message);
    }

}
