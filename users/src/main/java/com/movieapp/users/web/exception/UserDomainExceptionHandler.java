package com.movieapp.users.web.exception;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDomainExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<RestExceptionMessage> handleEntityNotFoundException(EntityNotFoundException ex,
                                                                              WebRequest request) {
        RestExceptionMessage message = new RestExceptionMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(EntityExistsException.class)
    public ResponseEntity<RestExceptionMessage> handleEntityExistsException(EntityExistsException ex,
                                                                            WebRequest request) {
        RestExceptionMessage message = new RestExceptionMessage(LocalDateTime.now(), ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(message);
    }

}
