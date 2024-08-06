package com.movieapp.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "\"users\"")
public class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Email @NotBlank @Column(nullable = false, unique = true) String email;
    private @NotBlank String password;
    private @NotBlank String firstName;
    private @NotBlank String lastName;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    public void addRole(Role role) {
        boolean containsRole = this.roles.contains(role);
        if (!containsRole) {
            this.roles.add(role);
        }
    }

}
