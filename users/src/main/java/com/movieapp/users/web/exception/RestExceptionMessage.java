package com.movieapp.users.web.exception;

import java.time.LocalDateTime;

public record RestExceptionMessage(LocalDateTime timeStamp,
                                   String message,
                                   Integer statusCode) {}
