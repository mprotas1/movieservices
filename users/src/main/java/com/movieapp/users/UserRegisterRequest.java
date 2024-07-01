package com.movieapp.users;

public record UserRegisterRequest(String email, String password, String firstName, String lastName) {
}
