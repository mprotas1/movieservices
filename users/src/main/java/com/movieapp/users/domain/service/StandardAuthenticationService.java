package com.movieapp.users.domain.service;

import com.movieapp.users.domain.mapper.UserMapper;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class StandardAuthenticationService implements AuthenticationService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;

    @Override
    @Transactional
    public UserDTO register(@Valid UserRegisterRequest request) {
        log.debug("Registering the user with email address: {}", request.email());
        User user = userMapper.toEntity(request);
        User registered = userRepository.save(user);
        User userWithRole = roleService.addToRole(registered, RoleType.USER);
        UserDTO dto = userMapper.toDTO(userWithRole);
        log.debug("Successfully create user with e-mail: {}", dto.email());
        return dto;
    }

    @Override
    public UserDTO authenticate(@Valid UserLoginRequest request) {
        log.debug("Authenticating the user with email address: {}", request.email());
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }

}
