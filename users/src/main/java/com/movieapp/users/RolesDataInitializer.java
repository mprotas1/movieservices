package com.movieapp.users;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RolesDataInitializer {
    private static final Logger log = LoggerFactory.getLogger(RolesDataInitializer.class.getName());
    private final UsersRoleService roleService;
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
        roleService.addRole(Role.ofRoleName(role));
    }

    private boolean roleExists(RoleType roleType) {
        return roleRepository.findByRoleName(roleType).isPresent();
    }

}
