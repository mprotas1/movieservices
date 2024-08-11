package com.movieapp.users.domain;

import com.movieapp.users.UserDTO;
import com.movieapp.users.UserRegisterRequest;

import java.util.List;

interface UserService {
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    void deleteById(Long id);
}
