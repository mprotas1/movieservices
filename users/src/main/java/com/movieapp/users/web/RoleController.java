package com.movieapp.users.web;

import com.movieapp.users.domain.RoleType;
import com.movieapp.users.UserDTO;
import com.movieapp.users.domain.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/users/roles")
@RequiredArgsConstructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/{role}")
    ResponseEntity<List<UserDTO>> findAllByRole(@PathVariable RoleType role) {
        List<UserDTO> usersByRole = roleService.findUsersByRole(role);
        return ResponseEntity.ok(usersByRole);
    }

}
