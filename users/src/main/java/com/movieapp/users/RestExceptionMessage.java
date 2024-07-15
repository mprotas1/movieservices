package com.movieapp.users;

import org.springframework.boot.context.properties.bind.DefaultValue;

import java.time.LocalDateTime;

record RestExceptionMessage(LocalDateTime timeStamp, String message, Integer statusCode) {}
