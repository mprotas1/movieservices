package com.movieapp.cinemas.web;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
class CinemaExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolationException(ConstraintViolationException constraintException) {
        String message = getConstraintViolationMessage(constraintException);
        return problemDetailEntity(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<ProblemDetail> handleEntityExistsException(EntityExistsException entityExistsException) {
        return problemDetailEntity(HttpStatus.CONFLICT, entityExistsException.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleEntityNotFoundException(EntityNotFoundException entityNotFoundException) {
        return problemDetailEntity(HttpStatus.NOT_FOUND, entityNotFoundException.getMessage());
    }

    private String getConstraintViolationMessage(ConstraintViolationException ex) {
        return ex.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .reduce((s1, s2) -> s1 + ", " + s2)
                .orElse("Unexpected constraint violation");
    }

    private ResponseEntity<ProblemDetail> problemDetailEntity(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(ProblemDetail.forStatusAndDetail(status, message));
    }

}

