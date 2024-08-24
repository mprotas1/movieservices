package com.movieapp.users.domain.service;

import com.movieapp.users.domain.entity.Role;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.domain.repository.RoleRepository;
import com.movieapp.users.domain.repository.UserRepository;
import com.movieapp.users.web.dto.UserDTO;
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
    private final UserRepository userRepository;

    @Override
    public Role findRole(RoleType role) {
        return roleRepository.findByRoleType(role)
                .orElseThrow(() -> new EntityNotFoundException("Could not find role with name: " + role));
    }

    @Override
    @Transactional
    public Role addRole(Role role) {
        log.info("Adding role: {}", role.getRoleType());
        checkIfRoleExists(role);
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public User addToRole(User user, RoleType roleType) {
        Role role = findRole(roleType);
        user.addRole(role);
        return userRepository.save(user);
    }

    @Override
    public List<UserDTO> findUsersByRole(RoleType role) {
        return roleRepository.findAllUsersWithRole(role).stream()
                .flatMap(users -> users.stream().map(user -> new UserDTO(user.getId(), user.getEmail(), user.getRoles()))).toList();
    }

    @Override
    public boolean roleExists(RoleType role) {
        return roleRepository.existsByRoleType(role);
    }

    private void checkIfRoleExists(Role role) {
        if(roleExists(role.getRoleType())) {
            throw new EntityExistsException("Role with name: " + role.getRoleType() + " already exists");
        }
    }
    
}
