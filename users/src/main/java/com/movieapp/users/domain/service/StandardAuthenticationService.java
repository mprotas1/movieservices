package com.movieapp.users.domain.service;

import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.exception.FailedAuthenticationException;
import com.movieapp.users.domain.mapper.UserMapper;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserAuthenticationResponse;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class StandardAuthenticationService implements AuthenticationService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserAuthenticationResponse register(@Valid UserRegisterRequest request) {
        log.debug("Registering the user with email address: {}", request.email());
        User user = userMapper.toEntity(request);
        User registered = userRepository.save(user);
        User userWithRole = roleService.addToRole(registered, RoleType.USER);
        String token = tokenService.generateToken(userWithRole);
        UserDTO userDTO = userMapper.toDTO(userWithRole);
        log.debug("Successfully created user with e-mail: {}", userWithRole.getEmail());
        return new UserAuthenticationResponse(userDTO, token);
    }

    @Override
    @Transactional
    public UserAuthenticationResponse authenticate(@Valid UserLoginRequest request) {
        log.debug("Authenticating the user with email address: {}", request.email());
        User details = (User) userDetailsService.loadUserByUsername(request.email());
        if (passwordIsInvalid(request, details)) {
            log.debug("Could not authenticate user with e-mail: {}", request.email());
            throw new FailedAuthenticationException("Authentication of the user: " + request.email() + " failed");
        }
        String token = tokenService.generateToken(details);
        log.debug("Successfully authenticated user with e-mail: {}", request.email());
        UserDTO dto = userMapper.toDTO(details);
        return new UserAuthenticationResponse(dto, token);
    }

    private boolean passwordIsInvalid(UserLoginRequest request, UserDetails details) {
        return !passwordEncoder.matches(request.password(), details.getPassword());
    }

}
