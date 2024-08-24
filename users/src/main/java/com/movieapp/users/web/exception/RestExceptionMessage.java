package com.movieapp.users.web.exception;

import java.time.LocalDateTime;

record RestExceptionMessage(LocalDateTime timeStamp,
                            String message,
                            Integer statusCode) {}
