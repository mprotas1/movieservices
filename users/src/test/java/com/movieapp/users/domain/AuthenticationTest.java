package com.movieapp.users.domain;

import com.movieapp.users.domain.entity.Role;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserLoginRequest;
import com.movieapp.users.web.dto.UserRegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationTest {
    private UserRegisterRequest validRegisterRequest;
    private UserLoginRequest validLoginRequest;
    private User validUser;

    @InjectMocks
    private StandardAuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleService roleService;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        validRegisterRequest = new UserRegisterRequest("someemail@gmail.com", "password", "John", "Doe");
        validLoginRequest = new UserLoginRequest("someemail@gmail.com", "password");
        validUser = setUpUser();
    }

    // 1. Test the registration of a new user
    @Test
    void shouldRegisterNewUserWithValidCredentials() {
        // when
        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userMapper.toEntity(any(UserRegisterRequest.class))).thenReturn(any(User.class));
        when(roleService.addToRole(any(User.class), eq(RoleType.USER))).thenReturn(any(User.class));
        when(userMapper.toDTO(validUser)).thenReturn(new UserDTO(validUser.getId(), validUser.getEmail(), validUser.getRoles()));

        // then
        UserDTO user = authenticationService.register(validRegisterRequest);
        assertNotNull(user);
        assertNotNull(user.id());
        assertNotNull(user.email());
        assertEquals(1, user.roles().size());
    }

    // 2. Test the login of an existing user
    @Test
    void shouldAuthenticateUserWithValidCredentials() {
        // given

        // when

        // then

    }

    private User setUpUser() {
        User validUser = new User();
        validUser.setId(1L);
        validUser.setEmail(validRegisterRequest.email());
        validUser.setRoles(List.of(Role.ofRoleType(RoleType.USER)));
        validUser.setFirstName(validRegisterRequest.firstName());
        validUser.setLastName(validRegisterRequest.lastName());
        return validUser;
    }

}
