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
@Table(name = "\"user\"")
class User {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private @Email @NotBlank String email;
    private @NotBlank String password;
    private @NotBlank String firstName;
    private @NotBlank String lastName;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinTable(name = "", inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private List<Role> roles = new ArrayList<>();

    public static User register(UserRegisterRequest request) {
        User user = new User();
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        return user;
    }

    public void addRole(Role role) {
        boolean containsRole = this.roles.contains(role);
        if (!containsRole) {
            this.roles.add(role);
        }
    }

}
