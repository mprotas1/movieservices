package com.movieapp.users;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RolesDataInitializer {
    private final RoleService roleService;
    private final RoleRepository roleRepository;

    public RolesDataInitializer(UsersRoleService roleService, RoleRepository roleRepository) {
        this.roleService = roleService;
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void initializeRoles() {
        log.debug("Initializing User Role if they are not existing...");

        initializeSingleRole(RoleType.USER);
        initializeSingleRole(RoleType.MODERATOR);
        initializeSingleRole(RoleType.ADMIN);
    }

    private void initializeSingleRole(RoleType role) {
        if(roleExists(role)) {
            log.error("Role {} already exists - skipping adding of the Role", role);
            return;
        }
        log.debug("Initialized {} role", role);
        roleService.addRole(Role.ofRoleType(role));
    }

    private boolean roleExists(RoleType roleType) {
        return roleRepository.findByRoleType(roleType).isPresent();
    }

}
