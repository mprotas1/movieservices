package com.movieapp.users.domain.mapper;

import com.movieapp.users.domain.entity.Role;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.web.dto.UserDTO;
import com.movieapp.users.web.dto.UserRegisterRequest;
import com.movieapp.users.web.dto.UserUpdateRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    @InjectMocks
    UserMapper userMapper;
    @Mock
    PasswordEncoder encoder;

    private User exampleUser;

    @BeforeEach
    void init() {
        exampleUser = new User();
        exampleUser.setId(1L);
        exampleUser.setEmail("example.email@gmail.com");
        exampleUser.setRoles(List.of(Role.ofRoleType(RoleType.USER)));
    }

    @Test
    @DisplayName("Should map the User Entity to UserDTO")
    void shouldMapUserEntityToUserDTO() {
        UserDTO dto = userMapper.toDTO(exampleUser);

        assertNotNull(dto);
        assertEquals(exampleUser.getId(), dto.id());
        assertEquals(exampleUser.getEmail(), dto.email());
        assertEquals(exampleUser.getRoles(), dto.roles());
    }

    @Test
    void shouldMapUserRegisterRequestToUserEntity() {
        UserRegisterRequest request = new UserRegisterRequest("some.mail@gmail.com",
                "somehardpassword123",
                "John",
                "Doe");

        when(encoder.encode(request.password())).thenReturn("reallyencodedpassword");
        User user = userMapper.toEntity(request);

        assertNotNull(user);
        assertEquals(request.email(), user.getEmail());
        assertEquals(request.firstName(), user.getFirstName());
        assertEquals(request.lastName(), user.getLastName());
    }

    @Test
    void shouldUpdateUserEntity() {
        UserUpdateRequest updateData = new UserUpdateRequest("differentEmail@gmail.com",
                "DifferentFirstName",
                "DifferentLastName");
        User updatedEntity = userMapper.updateEntity(exampleUser,
                updateData);

        assertEquals(exampleUser.getEmail(), updatedEntity.getEmail());
        assertEquals(exampleUser.getFirstName(), updatedEntity.getFirstName());
        assertEquals(exampleUser.getLastName(), updatedEntity.getLastName());
    }
}
