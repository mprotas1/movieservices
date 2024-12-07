package com.movieapp.cinemas.domain.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class CinemaRoomEntityTest {

    @Test
    void shouldNotUpdateCinemaRoomCapacityWithIdenticalValue() {
        int oldCapacity = 100;
        CinemaRoom room = new CinemaRoom(1, oldCapacity, new Cinema());
        assertThrows(IllegalArgumentException.class, () -> room.updateCapacity(oldCapacity));
    }

}
