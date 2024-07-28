package com.movieapp.users;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class UserBusinessRulesTest {
    @Mock
    private RoleService roleService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private CredentialsUserService userService;

    @BeforeEach
    void setUp() {

    }

    @Test
    void shouldCreateUserWithValidCredentials() {
        UserRegisterRequest request = new UserRegisterRequest("someemail@gmail.com", "somepassword123", "Michał", "Protas");
        UserDTO dto = Mockito.mock(UserDTO.class);
        when(userService.register(request)).thenReturn(dto);

        UserDTO register = userService.register(request);
        System.out.println(register);
    }


    @Test
    void shouldRegisterUserWithValidCredentials() {
        // given
        UserRegisterRequest request = new UserRegisterRequest("testuser@gmail.com", "user", "Test", "User");
        User user = setUpUserFromRegisterRequest(request);

        // when
        when(userRepository.save(user)).thenReturn(user);

        // then
        UserDTO registered = userService.register(request);

        assertNotNull(registered);
        assertEquals(request.email(), registered.email());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldDeleteUserById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.ofNullable(any()));


    }

    private User setUpUserFromRegisterRequest(UserRegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(request.password());
        return user;
    }

}
