package com.movieapp.cinemas.service.model;

import java.util.List;
import java.util.UUID;

public record CinemaRoomDTO(UUID roomId,
                            UUID cinemaId,
                            int number,
                            int capacity,
                            List<SeatDTO> seats) {}
