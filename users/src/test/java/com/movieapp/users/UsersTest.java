package com.movieapp.users;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersTest {
    @Mock
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;

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
        when(roleRepository.findByRoleType(RoleType.USER)).thenReturn(Optional.of(Role.ofRoleType(RoleType.USER)));

        // then
        UserDTO registered = userService.register(request);

        assertNotNull(registered);
        assertEquals(request.email(), registered.email());
        verify(userRepository, times(1)).save(user);
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
