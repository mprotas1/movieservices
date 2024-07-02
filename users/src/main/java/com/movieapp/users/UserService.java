package com.movieapp.users;

public interface UserService {
    UserDTO register(UserRegisterRequest request);
    UserDTO findById(Long id);
}
