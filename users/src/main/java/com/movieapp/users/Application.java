package com.movieapp.users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public Application(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Role userRole = Role.ofRoleName(RoleType.USER);
        Role moderatorRole = Role.ofRoleName(RoleType.MODERATOR);
        Role adminRole = Role.ofRoleName(RoleType.ADMIN);
        roleRepository.save(userRole);
        roleRepository.save(moderatorRole);
        roleRepository.save(adminRole);
    }
}
