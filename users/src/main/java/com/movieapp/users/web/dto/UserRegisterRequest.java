package com.movieapp.users.web.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record UserRegisterRequest(@Email String email,
                           @Length(min = 5) String password,
                           String firstName,
                           String lastName) {
}
