package com.movieapp.screenings.interfaces.rest;

import com.movieapp.screenings.domain.exception.MovieDoesNotExistException;
import com.movieapp.screenings.domain.exception.OverlappingScreeningException;
import com.movieapp.screenings.domain.exception.ScreeningRoomDoesNotExistException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class ScreeningExceptionHandler {

    @ExceptionHandler(value = {
            EntityNotFoundException.class,
            MovieDoesNotExistException.class,
            ScreeningRoomDoesNotExistException.class
    })
    ResponseEntity<ProblemDetail> handleNotFound(Exception exception) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, exception.getMessage()))
                .build();
    }

    @ExceptionHandler(value = {
            IllegalArgumentException.class,
            OverlappingScreeningException.class
    })
    ResponseEntity<ProblemDetail> handleConstraintException(Exception exception) {
        return ResponseEntity.of(ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, exception.getMessage()))
                .build();
    }

}
