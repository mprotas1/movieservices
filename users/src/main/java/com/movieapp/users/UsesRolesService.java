package com.movieapp.users;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsesRolesService implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role findRole(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Could not find role with name: " + roleName));
    }

    @Override
    public void addRole(Role role) {
        log.info("Adding role: {}", role.getRoleName());
        roleRepository.save(role);
    }

    @Override
    public void addToRole(User user, Role role) {
        user.addRole(role);
    }

    @Override
    public void addToRole(User user, String roleName) {
        Role role = findRole(roleName);
        addToRole(user, role);
    }

    @Override
    public List<User> findUsersByRole(String roleName) {
        return roleRepository.findAllUsersWithRole(roleName)
                .orElseThrow(() -> new EntityNotFoundException("Could not find users for role name: " + roleName));
    }
}
