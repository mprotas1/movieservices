package com.movieapp.users;

import java.time.LocalDateTime;

record RestExceptionMessage(LocalDateTime timeStamp, String message, Integer statusCode) {}
