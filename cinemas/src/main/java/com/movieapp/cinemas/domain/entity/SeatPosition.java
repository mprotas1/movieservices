package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;
import org.springframework.util.Assert;

@Embeddable
public record SeatPosition(int row, int seatNumber) {

    public SeatPosition {
        // Assert.isTrue(row > 0, "Row must be greater than 0");
        // Assert.isTrue(column > 0, "Column must be greater than 0");
    }

}
