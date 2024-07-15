package com.movieapp.users;

import java.util.List;

record UserDTO(Long id, String email, List<Role> roles) {}
