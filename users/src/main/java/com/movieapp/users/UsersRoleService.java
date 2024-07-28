package com.movieapp.users;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class UsersRoleService implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role findRole(RoleType role) {
        return roleRepository.findByRoleType(role)
                .orElseThrow(() -> new EntityNotFoundException("Could not find role with name: " + role));
    }

    @Override
    @Transactional
    public Role addRole(Role role) {
        log.info("Adding role: {}", role.getRoleType());
        if(isRoleExisting(role)) {
            throw new EntityExistsException("Role with name: " + role.getRoleType() + " already exists");
        }
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void addToRole(User user, RoleType roleType) {
        Role role = findRole(roleType);
        addToRole(user, role);
    }

    @Override
    @Transactional
    public void addToRole(User user, Role role) {
        log.debug("Adding user: {} to the Role: {}", user.getEmail(), role.getRoleType().name());
        user.addRole(role);
    }

    @Override
    public List<UserDTO> findUsersByRole(RoleType role) {
        return roleRepository.findAllUsersWithRole(role).stream()
                .flatMap(users -> users.stream().map(user -> new UserDTO(user.getId(), user.getEmail(), user.getRoles()))).toList();
    }

    private boolean isRoleExisting(Role role) {
        return roleRepository.findByRoleType(role.getRoleType()).isPresent();
    }

}
