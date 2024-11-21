package com.movieapp.users.domain.service;

import com.movieapp.users.domain.entity.Role;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.exception.FailedAuthenticationException;
import com.movieapp.users.domain.mapper.UserMapper;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserAuthenticationResponse;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationMockTest {
    private UserRegisterRequest registerRequest;
    private UserLoginRequest loginRequest;
    private User validUser;

    @InjectMocks
    private StandardAuthenticationService authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UsersRoleService roleService;
    @Mock
    private UserMapper userMapper;
    @Mock
    private TokenService tokenService;
    @Mock
    private UserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        registerRequest = new UserRegisterRequest("someemail@gmail.com", "password", "John", "Doe");
        loginRequest = new UserLoginRequest("someemail@gmail.com", "password");
        validUser = setUpUser();
    }

    @Test
    void shouldRegisterNewUserWithValidCredentials() {
        when(userRepository.save(any(User.class))).thenReturn(validUser);
        when(userMapper.toEntity(any(UserRegisterRequest.class))).thenReturn(validUser);
        when(roleService.addToRole(validUser, RoleType.USER)).thenReturn(validUser);
        when(userMapper.toDTO(validUser)).thenReturn(new UserDTO(validUser.getId(), validUser.getEmail(), validUser.getRoles()));
        when(tokenService.generateToken(validUser)).thenReturn("token");

        UserAuthenticationResponse authenticationResponse = authenticationService.register(registerRequest);
        validateAuthenticationResponse(authenticationResponse);
    }

    @Test
    void shouldAuthenticateUserWithValidCredentials() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(validUser);
        when(passwordEncoder.matches(anyString(), eq(validUser.getPassword()))).thenReturn(true);
        when(userMapper.toDTO(validUser)).thenReturn(new UserDTO(validUser.getId(), validUser.getEmail(), validUser.getRoles()));
        when(tokenService.generateToken(any(User.class))).thenReturn("token");

        UserAuthenticationResponse authenticationResponse = authenticationService.authenticate(loginRequest);
        validateAuthenticationResponse(authenticationResponse);
    }

    @Test
    void shouldNotAuthenticateUserWithInvalidUsername() {
        String email = "someemail@gmail.com";
        when(userDetailsService.loadUserByUsername(email)).thenThrow(new UsernameNotFoundException("User with email: " + email + " not found"));
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void shouldNotAuthenticateUserWithInvalidPassword() {
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(validUser);
        when(passwordEncoder.matches(anyString(), eq(validUser.getPassword()))).thenReturn(false);
        assertThrows(FailedAuthenticationException.class, () -> authenticationService.authenticate(loginRequest));
    }

    @Test
    void shouldNotRegisterUserWithAlreadyExistingEmailAddress() {
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        assertThrows(EntityExistsException.class, () -> authenticationService.register(registerRequest));
    }

    void validateAuthenticationResponse(UserAuthenticationResponse authenticationResponse) {
        assertNotNull(authenticationResponse);
        assertNotNull(authenticationResponse.token());
        assertFalse(authenticationResponse.token().isEmpty());
    }

    private User setUpUser() {
        User validUser = new User();
        validUser.setId(1L);
        validUser.setEmail(registerRequest.email());
        validUser.setRoles(List.of(Role.ofRoleType(RoleType.USER)));
        validUser.setFirstName(registerRequest.firstName());
        validUser.setLastName(registerRequest.lastName());
        return validUser;
    }

}
