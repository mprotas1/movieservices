package com.movieapp.users.domain.service;

import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import jakarta.validation.Valid;

public interface AuthenticationService {
    UserDTO register(@Valid UserRegisterRequest request);
    UserDTO authenticate(UserLoginRequest request);
}
