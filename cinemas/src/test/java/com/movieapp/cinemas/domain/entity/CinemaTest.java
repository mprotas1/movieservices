package com.movieapp.cinemas.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CinemaTest {

    @Test
    @DisplayName("Should get sequential number of cinema room")
    void shouldGetSequentialNumber() {
        int capacity = 100;
        Cinema cinema = new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000", CountryCode.PL));

        int firstRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(firstRoomNumber, capacity, cinema));
        int secondRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(secondRoomNumber, capacity, cinema));
        int thirdRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(thirdRoomNumber, capacity, cinema));
        int fourthRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(thirdRoomNumber, capacity, cinema));

        assertEquals(1, firstRoomNumber);
        assertEquals(2, secondRoomNumber);
        assertEquals(3, thirdRoomNumber);
        assertEquals(4, fourthRoomNumber);
    }

    @Test
    @DisplayName("Should remove a room")
    void shouldRemoveRoom() {
        int capacity = 100;
        Cinema cinema = new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000", CountryCode.PL));
        CinemaRoom room = new CinemaRoom(1, capacity, cinema);
        cinema.addRoom(room);
        assertTrue(cinema.getRooms().contains(room));
        cinema.removeRoom(room);
        assertTrue(cinema.getRooms().isEmpty());
    }

    @Test
    @DisplayName("Should not remove a room that does not exist")
    void shouldNotRemoveRoomThatDoesNotExist() {
        int capacity = 100;
        Cinema cinema = new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000", CountryCode.PL));
        CinemaRoom room = new CinemaRoom(1, capacity, cinema);
        CinemaRoom room2 = new CinemaRoom(2, capacity, cinema);
        cinema.addRoom(room);
        assertTrue(cinema.getRooms().contains(room));
        cinema.removeRoom(room2);
        assertTrue(cinema.getRooms().contains(room));
    }

    @Test
    @DisplayName("Should fill sequence gap when room is removed")
    void shouldFillSequenceGapWhenRoomIsRemoved() {
        int capacity = 100;
        Cinema cinema = new Cinema("Cinema Name", new Address("Blank Street", "Blank City", "00-000", CountryCode.PL));

        int firstRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(firstRoomNumber, capacity, cinema));
        int secondRoomNumber = cinema.getNextRoomNumber();
        CinemaRoom secondRoom = new CinemaRoom(secondRoomNumber, capacity, cinema);
        cinema.addRoom(secondRoom);
        int thirdRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(thirdRoomNumber, capacity, cinema));
        int fourthRoomNumber = cinema.getNextRoomNumber();
        cinema.addRoom(new CinemaRoom(thirdRoomNumber, capacity, cinema));

        assertEquals(1, firstRoomNumber);
        assertEquals(2, secondRoomNumber);
        assertEquals(3, thirdRoomNumber);
        assertEquals(4, fourthRoomNumber);

        cinema.removeRoom(secondRoom);
        int fifthRoomNumber = cinema.getNextRoomNumber();
        CinemaRoom fifthRoom = new CinemaRoom(fifthRoomNumber, capacity, cinema);
        cinema.addRoom(fifthRoom);

        assertEquals(1, firstRoomNumber);
        assertEquals(3, thirdRoomNumber);
        assertEquals(4, fourthRoomNumber);
        assertEquals(2, fifthRoomNumber);
        assertEquals(2, fifthRoom.getNumber());
    }

}
