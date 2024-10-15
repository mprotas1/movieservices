package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record SeatPosition(@Column(name = "`row_number`") int rowNumber, int seatNumber) {}
