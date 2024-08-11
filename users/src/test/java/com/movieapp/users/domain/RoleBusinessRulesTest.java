package com.movieapp.users.domain;

import com.movieapp.users.UserDTO;
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
class RoleBusinessRulesTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private UsersRoleService usersRoleService;

    @Mock
    private UserRepository userRepository;

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
        when(roleRepository.existsByRoleType(RoleType.USER)).thenReturn(false);

        Role createdRole = usersRoleService.addRole(role);

        assertNotNull(createdRole);
        assertEquals(role.getRoleType(), createdRole.getRoleType());
        verify(roleRepository, times(1)).save(role);
        verify(roleRepository, times(1)).existsByRoleType(any());
    }

    @Test
    void shouldRejectCreatingRoleIfItExists() {
        when(roleRepository.existsByRoleType(RoleType.USER)).thenReturn(true);
        assertThrows(EntityExistsException.class, () -> usersRoleService.addRole(userRole));
        verify(roleRepository, times(1)).existsByRoleType(RoleType.USER);
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
        RoleType roleType = RoleType.USER;
        User user = getTemplateUser();

        when(roleRepository.findByRoleType(roleType)).thenReturn(Optional.of(userRole));

        usersRoleService.addToRole(user, roleType);
        assertTrue(user.getRoles().stream().map(Role::getRoleType).toList().contains(roleType));
        verify(roleRepository, times(1)).findByRoleType(roleType);
    }

    @Test
    void shouldCheckWhetherRoleExists() {
        RoleType role = RoleType.USER;
        when(roleRepository.existsByRoleType(role)).thenReturn(Boolean.TRUE);
        boolean isRoleExisting = usersRoleService.roleExists(role);
        assertTrue(isRoleExisting);
        verify(roleRepository, times(1)).existsByRoleType(role);
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
