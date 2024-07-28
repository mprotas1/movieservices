package com.movieapp.users;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UsersRoleService usersRoleService;

    private Role userRole;
    private Role moderatorRole;
    private Role adminRole;

    @BeforeEach
    void setUp() {
        userRole = Role.ofRoleType(RoleType.USER);
        moderatorRole = Role.ofRoleType(RoleType.MODERATOR);
        adminRole = Role.ofRoleType(RoleType.ADMIN);
    }

    @Test
    void shouldCreateRoleByRoleType() {
        Role role = Role.ofRoleType(RoleType.USER);

        when(roleRepository.save(role)).thenReturn(role);
        when(roleRepository.findByRoleType(RoleType.USER)).thenReturn(Optional.empty());

        Role createdRole = usersRoleService.addRole(role);

        assertNotNull(createdRole);
        assertEquals(role.getRoleType(), createdRole.getRoleType());
        verify(roleRepository, times(1)).save(role);
        verify(roleRepository, times(1)).findByRoleType(RoleType.USER);
    }

    @Test
    void shouldRejectCreatingRoleIfItExists() {
        when(roleRepository.findByRoleType(RoleType.USER)).thenReturn(Optional.of(userRole));

        assertThrows(EntityExistsException.class, () -> usersRoleService.addRole(userRole));
        verify(roleRepository, times(1)).findByRoleType(RoleType.USER);
    }

    @Test
    void shouldFindAllUsersByRoleType() {
        List<User> mockList = List.of(Mockito.mock(), Mockito.mock());

        when(roleRepository.findAllUsersWithRole(RoleType.USER)).thenReturn(Optional.of(mockList));

        List<UserDTO> usersByRole = usersRoleService.findUsersByRole(RoleType.USER);
        assertNotNull(usersByRole);
        assertEquals(mockList.size(), usersByRole.size());
        verify(roleRepository, times(1)).findAllUsersWithRole(RoleType.USER);
    }

    @Test
    void shouldFindAdminRoleIfItExists () {
        when(roleRepository.findByRoleType(RoleType.ADMIN)).thenReturn(Optional.of(adminRole));

        Role fetchedRole = usersRoleService.findRole(RoleType.ADMIN);

        assertNotNull(fetchedRole);
        assertEquals(RoleType.ADMIN, fetchedRole.getRoleType());
        verify(roleRepository, times(1)).findByRoleType(RoleType.ADMIN);
    }

    @Test
    void shouldNotFindAdminRoleIfItDoesNotExist () {
        when(roleRepository.findByRoleType(RoleType.ADMIN)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> usersRoleService.findRole(RoleType.ADMIN));
        verify(roleRepository, times(1)).findByRoleType(RoleType.ADMIN);
    }

    @Test
    void shouldAddUserToRoleByRoleType() {
        // given
        RoleType roleType = RoleType.USER;
        User user = getTemplateUser();

        // when
        when(roleRepository.findByRoleType(roleType)).thenReturn(Optional.of(userRole));

        // then
        usersRoleService.addToRole(user, roleType);
        assertTrue(user.getRoles().stream().map(Role::getRoleType).toList().contains(roleType));
        verify(roleRepository, times(1)).findByRoleType(roleType);
    }

    private User getTemplateUser() {
        User user = new User();
        user.setFirstName("User");
        user.setLastName("Test");
        user.setEmail("testemail@gmail.com");
        user.setPassword("password123");
        return user;
    }

}
