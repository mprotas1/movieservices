package com.movieapp.users.domain;

import com.movieapp.users.UserDTO;
import com.movieapp.users.UserLoginRequest;
import com.movieapp.users.UserRegisterRequest;

public interface AuthenticationService {

    UserDTO register(UserRegisterRequest request);
    UserDTO authenticate(UserLoginRequest request);

}
