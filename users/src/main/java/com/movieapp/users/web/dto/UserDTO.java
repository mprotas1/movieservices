package com.movieapp.users.web.dto;

import com.movieapp.users.domain.entity.Role;

import java.util.List;

public record UserDTO(Long id, String email, List<Role> roles) {}
