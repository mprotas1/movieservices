package com.movieapp.reservations.interfaces.client;

import com.movieapp.model.UserDTO;

import java.util.Optional;

public interface UserClient {
    Optional<UserDTO> getUser(Long id);
}
