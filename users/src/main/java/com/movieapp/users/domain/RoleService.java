package com.movieapp.users.domain;

import com.movieapp.users.domain.entity.Role;
import com.movieapp.users.domain.entity.RoleType;
import com.movieapp.users.domain.entity.User;
import com.movieapp.users.web.dto.UserDTO;

import java.util.List;

public interface RoleService {
    Role findRole(RoleType roleType);
    Role addRole(Role role);
    User addToRole(User user, RoleType role);
    List<UserDTO> findUsersByRole(RoleType role);
    boolean roleExists(RoleType role);
}
