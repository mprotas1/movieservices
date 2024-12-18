package com.movieapp.model;

import java.util.List;

public record UserDTO(Long id, String email, List<String> roles) {}
