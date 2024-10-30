package com.movieapp.cinemas.domain.strategy;

import com.movieapp.cinemas.domain.CinemaPropertiesAccessor;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.Seat;
import com.movieapp.cinemas.domain.entity.SeatPosition;
import com.movieapp.cinemas.domain.entity.SeatType;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUpdateSeatsStrategy implements UpdateSeatsStrategy {
    private final int SEATS_PER_ROW;

    public DefaultUpdateSeatsStrategy() {
        this.SEATS_PER_ROW = getSeatPerRowFromProperties();
    }

    @Override
    public List<Seat> updateSeats(CinemaRoom room, int newCapacity) {
        int actualCapacity = room.getCapacity();

        if(newCapacity == actualCapacity) {
            return room.getSeats();
        }

        int roomRows = getNumberOfRows(newCapacity);
        if(newCapacity > actualCapacity) {
            return addSeats(newCapacity, roomRows, room);
        }

        return removeSeats(newCapacity, room);
    }

    private List<Seat> addSeats(int targetCapacity, int rows, CinemaRoom room) {
        List<Seat> result = new ArrayList<>(room.getSeats());
        int missingSeats = Math.max(0, targetCapacity - room.getCapacity());
        int highestRow = getHighestExistingRow(room);

        int nextRow = Math.min(highestRow + 1, rows);

        while(missingSeats > 0) {
            for(int i = 1; i <= SEATS_PER_ROW; i++) {
                if(missingSeats == 0) {
                    break;
                }

                SeatPosition position = new SeatPosition(nextRow, i);
                Seat newSeat = new Seat(position, SeatType.STANDARD, room);
                result.add(newSeat);
                missingSeats--;
            }
            nextRow++;
        }

        return result;
    }

    private List<Seat> removeSeats(int targetCapacity, CinemaRoom room) {
        int seatsToRemove = room.getCapacity() - targetCapacity <= 0 ? targetCapacity : room.getCapacity() - targetCapacity;
        List<Seat> result = new ArrayList<>(room.getSeats());
        result = sortedSeats(result);

        for(int i = 0; i < seatsToRemove; i++) {
            result.removeLast();
        }

        return result;
    }

    private static Integer getHighestExistingRow(CinemaRoom room) {
        return room.getSeats().stream()
                .map(Seat::getPosition)
                .map(SeatPosition::rowNumber)
                .max(Integer::compareTo)
                .orElse(0);
    }

    private List<Seat> sortedSeats(List<Seat> result) {
        return result.stream()
                .sorted(Comparator.comparing(seat -> ((Seat) seat)
                                .getPosition()
                                .rowNumber())
                        .reversed()
                        .thenComparing(seat -> ((Seat) seat)
                                .getPosition()
                                .seatNumber())
                        .reversed())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private int getNumberOfRows(int capacity) {
        return Math.round((float) capacity / SEATS_PER_ROW);
    }

    private int getSeatPerRowFromProperties() {
        CinemaPropertiesAccessor cinemaPropertiesAccessor = new CinemaPropertiesAccessor();
        return cinemaPropertiesAccessor.getSeatPerRowFromProperties();
    }

}
