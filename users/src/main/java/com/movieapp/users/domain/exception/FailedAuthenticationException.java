package com.movieapp.users.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class FailedAuthenticationException extends RuntimeException {

    public FailedAuthenticationException(String message) {
        super(message);
    }

}
