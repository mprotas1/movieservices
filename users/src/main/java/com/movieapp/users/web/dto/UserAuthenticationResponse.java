package com.movieapp.users.web.dto;

public record UserAuthenticationResponse(UserDTO user, String token) {}