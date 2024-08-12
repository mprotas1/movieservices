package com.movieapp.users.domain;

import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserUpdateRequest;

import java.util.List;

public interface UserService {
    UserDTO findById(Long id);
    UserDTO update(Long userId, UserUpdateRequest updateData);
    List<UserDTO> findAll();
    void deleteById(Long id);
}
