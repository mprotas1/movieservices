package com.movieapp.users.domain;

import com.movieapp.users.UserDTO;

import java.util.List;

interface RoleService {
    Role findRole(RoleType roleType);
    Role addRole(Role role);
    User addToRole(User user, RoleType role);
    List<UserDTO> findUsersByRole(RoleType role);
    boolean roleExists(RoleType role);
}
