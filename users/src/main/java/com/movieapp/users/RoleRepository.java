package com.movieapp.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(@Param(value = "role_type") RoleType roleType);

    @Query("SELECT u FROM User u JOIN u.roles r WHERE r.roleType = :role_type")
    Optional<List<User>> findAllUsersWithRole(@Param(value = "role_type") RoleType roleType);

}
