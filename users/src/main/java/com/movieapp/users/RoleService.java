package com.movieapp.users;

import java.util.List;

interface RoleService {
    Role findRole(RoleType roleType);
    Role addRole(Role role);
    void addToRole(User user, RoleType role);
    List<UserDTO> findUsersByRole(RoleType role);
    boolean roleExists(RoleType role);
}
