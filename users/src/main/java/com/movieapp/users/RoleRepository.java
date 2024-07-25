package com.movieapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(RoleType roleName);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleName = :roleName")
    Optional<List<User>> findAllUsersWithRole(RoleType roleName);
}
