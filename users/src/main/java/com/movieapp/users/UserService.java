package com.movieapp.users;

import java.util.List;

interface UserService {
    UserDTO register(UserRegisterRequest request);
    UserDTO findById(Long id);
    List<UserDTO> findAll();
    void deleteById(Long id);
}
