package com.movieapp.users;

import java.util.List;

interface RoleService {
    Role findRole(String roleName);
    void addRole(Role role);
    void addToRole(User user, Role role);
    void addToRole(User user, String roleName);
    List<User> findUsersByRole(String role);
}
