package com.movieapp.users.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(@Email @NotBlank String email,
                                @NotBlank String firstName,
                                @NotBlank String lastName) {}
