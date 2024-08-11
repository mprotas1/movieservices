package com.movieapp.users.web;

import java.time.LocalDateTime;

record RestExceptionMessage(LocalDateTime timeStamp,
                            String message,
                            Integer statusCode) {}
