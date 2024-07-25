package com.movieapp.users;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
class UsersRoleService implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findRole(RoleType role) {
        return roleRepository.findByRoleName(role)
                .orElseThrow(() -> new EntityNotFoundException("Could not find role with name: " + role));
    }

    @Override
    public void addRole(Role role) {
        log.info("Adding role: {}", role.getRoleName());
        if(roleRepository.findByRoleName(role.getRoleName()).isPresent()) {
            throw new EntityExistsException("Role with name: " + role.getRoleName() + " already exists");
        }
        roleRepository.save(role);
    }

    @Override
    public void addToRole(User user, Role role) {
        log.debug("Adding user: {} to the Role: {}", user.getEmail(), role.getRoleName().name());
        user.addRole(role);
    }

    @Override
    public void addToRole(User user, RoleType roleType) {
        Role role = findRole(roleType);
        addToRole(user, role);
    }

    @Override
    public List<UserDTO> findUsersByRole(RoleType role) {
        return roleRepository.findAllUsersWithRole(role).stream()
                .flatMap(users -> users.stream().map(user -> new UserDTO(user.getId(), user.getEmail(), user.getRoles()))).toList();
    }
}
