package com.movieapp.users;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor
final class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, name = "name")
    private String roleName;
    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    private List<User> users = new ArrayList<>();

    public static Role ofRoleName(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        return role;
    }

}
