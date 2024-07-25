package com.movieapp.users;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UsersRoleService usersRoleService;

    private Role adminRole;
    private User user;

    @BeforeEach
    void setUp() {
        adminRole = new Role();
        adminRole.setRoleName(RoleType.ADMIN);

        user = new User();
        user.setEmail("test@example.com");
        user.setFirstName("FirstName");
        user.setLastName("LastName");
    }

    @Test
    void shouldFindAdminRoleIfItExists () {
        when(roleRepository.findByRoleName(RoleType.ADMIN)).thenReturn(Optional.of(adminRole));

        Role fetchedRole = usersRoleService.findRole(RoleType.ADMIN);
        assertNotNull(fetchedRole);
        assertEquals(RoleType.ADMIN, fetchedRole.getRoleName());
        verify(roleRepository, times(1)).findByRoleName(RoleType.ADMIN);
    }

    @Test
    void shouldNotFindAdminRoleIfItDoesNotExist () {
        when(roleRepository.findByRoleName(RoleType.ADMIN)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> usersRoleService.findRole(RoleType.ADMIN));
    }

    @Test
    void shouldAddUserToSpecificRole() {
    }

    Role buildUserRole() {
        return Role.ofRoleName(RoleType.USER);
    }

}
