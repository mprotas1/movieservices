package com.movieapp.users;

interface UserService {
    UserDTO register(UserRegisterRequest request);
    UserDTO findById(Long id);
    void deleteById(Long id);
}
