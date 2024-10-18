package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.CinemaId;
import com.movieapp.cinemas.domain.entity.CinemaRoom;
import com.movieapp.cinemas.domain.entity.CinemaRoomId;

import java.util.*;

public class InMemoryCinemaRoomRepository implements CinemaRoomRepository {
    private final Map<CinemaRoomId, CinemaRoom> rooms = new HashMap<>();

    @Override
    public CinemaRoom save(CinemaRoom cinemaRoom) {
        rooms.put(cinemaRoom.getId(), cinemaRoom);
        return cinemaRoom;
    }

    @Override
    public List<CinemaRoom> findAll() {
        return new ArrayList<>(rooms.values());
    }

    @Override
    public List<CinemaRoom> findByCinemaId(CinemaId cinemaId) {
        return rooms.values().stream()
                .filter(cinemaRoom -> cinemaRoom.getCinemaId().equals(cinemaId))
                .toList();
    }

    @Override
    public Optional<CinemaRoom> findById(CinemaRoomId cinemaRoomId) {
        return rooms.values().stream()
                .filter(room -> room.getId().equals(cinemaRoomId))
                .findFirst();
    }

    @Override
    public Optional<CinemaRoom> findByCinemaAndNumber(CinemaId cinemaId, int number) {
        return rooms.values().stream()
                .filter(room -> room.getCinemaId().equals(cinemaId) && room.getNumber() == number)
                .findFirst();
    }

    @Override
    public void deleteById(CinemaRoomId cinemaRoomId) {
        rooms.remove(cinemaRoomId);
    }

    @Override
    public void deleteAll() {
        rooms.clear();
    }

}
