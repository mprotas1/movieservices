package com.movieapp.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Collections;
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
    private RoleRepository roleRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;
    @InjectMocks
    private CredentialsUserService userService;

    @Test
    void shouldCreateUserWithValidCredentials() {
        UserRegisterRequest request = new UserRegisterRequest("someemail@gmail.com", "somepassword123", "Michał", "Protas");
        User user = setUpUserFromRegisterRequest(request);

        when(userRepository.save(any())).thenReturn(user);
        when(mapper.toEntity(request)).thenReturn(user);
        when(mapper.toDTO(user)).thenReturn(new UserDTO(user.getId(), user.getEmail(), user.getRoles()));

        UserDTO register = userService.register(request);

        assertNotNull(register);
        assertEquals(request.email(), register.email());
        assertEquals(1, register.roles().size());

        verify(userRepository, times(1)).save(any());
        verify(mapper, times(1)).toEntity(any(UserRegisterRequest.class));
        verify(mapper, times(1)).toDTO(any());
    }

    @Test
    void shouldDeleteUserById() {
        Long id = 1L;
        when(userRepository.findById(id)).thenReturn(Optional.of(mock(User.class)));
        userService.deleteById(id);
        verify(userRepository, times(1)).deleteById(id);
        verify(userRepository, times(1)).findById(id);
    }

    private User setUpUserFromRegisterRequest(UserRegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPassword(request.password());
        user.setRoles(Collections.singletonList(Role.ofRoleType(RoleType.USER)));
        return user;
    }

}
