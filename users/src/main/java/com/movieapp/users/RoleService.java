package com.movieapp.users;

import java.util.List;

interface RoleService {
    Role findRole(String roleName);
    void addRole(Role role);
    void addToRole(User user, Role role);
    List<User> findUsersByRole(String role);
}
