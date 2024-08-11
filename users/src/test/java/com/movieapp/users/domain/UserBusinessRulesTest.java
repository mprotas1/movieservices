package com.movieapp.users.domain;

import com.movieapp.users.UserMapper;
import com.movieapp.users.UserRegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
