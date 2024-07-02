package com.movieapp.users;

import java.time.LocalDateTime;

public record RestExceptionMessage(LocalDateTime timeStamp, String message, Integer statusCode) {
}
