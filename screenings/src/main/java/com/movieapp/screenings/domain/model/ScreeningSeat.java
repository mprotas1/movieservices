package com.movieapp.screenings.domain.model;

import com.movieapp.screenings.application.dto.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreeningSeat {
    private SeatId seatId;
    private ScreeningId screeningId;
    private int row;
    private int column;
    private SeatType seatType;
    private boolean isReserved;
    private Double price;

    public ScreeningSeat(ScreeningId screeningId, int row, int column, SeatType seatType) {
        this.seatId = new SeatId(UUID.randomUUID());
        this.screeningId = screeningId;
        this.row = row;
        this.column = column;
        this.seatType = seatType;
        this.isReserved = false;
    }

    public ScreeningSeat(SeatId seatId, ScreeningId screeningId, int row, int column, boolean isReserved, SeatType seatType) {
        this.seatId = seatId;
        this.screeningId = screeningId;
        this.row = row;
        this.column = column;
        this.seatType = seatType;
        this.isReserved = isReserved;
    }

}
