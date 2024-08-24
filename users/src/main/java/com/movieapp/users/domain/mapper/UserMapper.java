package com.movieapp.users.domain.mapper;

import com.movieapp.users.domain.entity.User;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserRegisterRequest;
import com.movieapp.users.web.dto.UserUpdateRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder encoder;

    public UserMapper(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getEmail(), user.getRoles());
    }

    public User toEntity(UserRegisterRequest request) {
        User user = new User();
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        return user;
    }

    public User updateEntity(User user, UserUpdateRequest updateData) {
        user.setFirstName(updateData.firstName());
        user.setLastName(updateData.lastName());
        user.setEmail(updateData.email());
        return user;
    }

}
