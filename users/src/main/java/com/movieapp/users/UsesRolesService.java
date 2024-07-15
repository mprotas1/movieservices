package com.movieapp.users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class UsesRolesService implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findRole(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Could not find role with name: " + roleName));
    }

    @Override
    public void addRole(Role role) {
        log.info("Adding role: {}", role.getRoleName());
        roleRepository.save(role);
    }

    @Override
    public void addToRole(User user, Role role) {

    }

    @Override
    public List<User> findUsersByRole(String role) {
        return List.of();
    }
}
