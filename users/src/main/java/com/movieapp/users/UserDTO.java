package com.movieapp.users;

import com.movieapp.users.domain.Role;

import java.util.List;

public record UserDTO(Long id, String email, List<Role> roles) {}
