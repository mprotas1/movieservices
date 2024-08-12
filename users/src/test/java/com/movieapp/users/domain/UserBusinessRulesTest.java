package com.movieapp.users.domain;

import com.movieapp.users.domain.entity.Role;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserRegisterRequest;
import com.movieapp.users.web.dto.UserUpdateRequest;
import jakarta.persistence.EntityNotFoundException;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserBusinessRulesTest {
    @InjectMocks
    private UserCrudService userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper mapper;

    private User validUser;
    private final UserUpdateRequest exampleUpdateRequest = new UserUpdateRequest("someotheremail@gmail.com",
                                                                              "someotherfirstname",
                                                                              "someotherlastname");

    @BeforeEach
    void setUp() {
        validUser = new User();
        validUser.setId(1L);
        validUser.setEmail("someemail@gmail.com");
        validUser.setPassword("somepassword");
        validUser.setFirstName("somefirstname");
        validUser.setLastName("somelastname");
        validUser.setRoles(Collections.singletonList(Role.ofRoleType(RoleType.USER)));
    }

    @Test
    @DisplayName("Should find all users")
    void shouldFindAllUsers() {
        when(userRepository.findAll()).thenReturn(getExampleUsers());
        List<UserDTO> users = userService.findAll();
        verify(userRepository, times(1)).findAll();
        verify(mapper, times(getExampleUsers().size())).toDTO(any(User.class));
        assertFalse(users.isEmpty());
        assertEquals(users.size(), getExampleUsers().size());
    }

    @Test
    @DisplayName("Should find user by id")
    void shouldFindUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(validUser));
        when(mapper.toDTO(any())).thenCallRealMethod();
        UserDTO userDTO = userService.findById(validUser.getId());
        verify(userRepository, times(1)).findById(validUser.getId());
        verify(mapper, times(1)).toDTO(validUser);
        assertEquals(userDTO.id(), validUser.getId());
        assertEquals(userDTO.email(), validUser.getEmail());
        assertEquals(userDTO.roles(), validUser.getRoles());
    }

    @Test
    @DisplayName("Should not find non-existing user")
    void shouldNotFindNonExistingUser() {
        Long id = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.findById(id));
        verify(userRepository, times(1)).findById(id);
        verify(mapper, never()).toDTO(any(User.class));
    }

    @Test
    @DisplayName("Should update user")
    void shouldUpdateUser() {
        Long id = 1L;
        User updatedEntity = getUpdatedUser(id);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(validUser));
        when(mapper.updateEntity(any(User.class), any(UserUpdateRequest.class))).thenReturn(updatedEntity);
        when(userRepository.save(any(User.class))).thenReturn(updatedEntity);
        when(mapper.toDTO(updatedEntity)).thenCallRealMethod();

        UserDTO updatedUser = userService.update(id, exampleUpdateRequest);

        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).save(updatedEntity);
        verify(mapper, times(1)).updateEntity(validUser, exampleUpdateRequest);
        verify(mapper, times(1)).toDTO(updatedEntity);

        assertEquals(updatedUser.id(), updatedEntity.getId());
        assertEquals(updatedUser.email(), updatedEntity.getEmail());
        assertEquals(updatedUser.roles(), updatedEntity.getRoles());
    }

    @Test
    @DisplayName("Should not update non-existing user")
    void shouldNotUpdateNonExistingUser() {
        Long id = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.update(id, exampleUpdateRequest));
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).save(any(User.class));
        verify(mapper, never()).updateEntity(any(User.class), any(UserUpdateRequest.class));
    }

    @Test
    @DisplayName("Should delete user by id")
    void shouldDeleteUserById() {
        Long id = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(validUser));
        userService.deleteById(id);
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, times(1)).deleteById(id);
    }

    @Test
    @DisplayName("Should not delete non-existing user")
    void shouldNotDeleteNonExistingUser() {
        Long id = 1L;
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> userService.deleteById(id));
        verify(userRepository, times(1)).findById(id);
        verify(userRepository, never()).deleteById(id);
    }

    private User getUpdatedUser(Long id) {
        User updatedEntity = new User();
        updatedEntity.setId(id);
        updatedEntity.setEmail(exampleUpdateRequest.email());
        updatedEntity.setFirstName(exampleUpdateRequest.firstName());
        updatedEntity.setLastName(exampleUpdateRequest.lastName());
        updatedEntity.setRoles(Collections.singletonList(Role.ofRoleType(RoleType.USER)));
        return updatedEntity;
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

    private List<User> getExampleUsers() {
        User user = setUpUserFromRegisterRequest(new UserRegisterRequest("test@gmail.com", "test123", "test", "test"));
        user.setId(1L);
        return List.of(user, user, user, user, user);
    }

}
