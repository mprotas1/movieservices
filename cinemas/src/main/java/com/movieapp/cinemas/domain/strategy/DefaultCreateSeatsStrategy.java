package com.movieapp.cinemas.domain.strategy;

import com.movieapp.cinemas.domain.CinemaPropertiesAccessor;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.Seat;
import com.movieapp.cinemas.domain.entity.SeatPosition;
import com.movieapp.cinemas.domain.entity.SeatType;

import java.util.ArrayList;
import java.util.List;

public class DefaultCreateSeatsStrategy implements CreateSeatsStrategy {
    private final int SEAT_PER_ROW;

    public DefaultCreateSeatsStrategy() {
        this.SEAT_PER_ROW = getSeatPerRowFromProperties();
    }

    @Override
    public List<Seat> createSeats(CinemaRoom room) {
        List<Seat> seats = new ArrayList<>();
        int rows = getNumberOfRows(room.getCapacity());

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < SEAT_PER_ROW; j++) {
                if(room.exceedsCapacity(seats.size())) {
                    break;
                }

                int row = i + 1;
                int seatNumber = j + 1;
                SeatPosition position = new SeatPosition(row, seatNumber);
                Seat seat = new Seat(position, SeatType.STANDARD, room);
                seats.add(seat);
            }
        }

        return seats;
    }

    private int getNumberOfRows(int capacity) {
        return Math.round((float) capacity / SEAT_PER_ROW);
    }

    private int getSeatPerRowFromProperties() {
        CinemaPropertiesAccessor cinemaPropertiesAccessor = new CinemaPropertiesAccessor();
        return cinemaPropertiesAccessor.getSeatPerRowFromProperties();
    }

}
