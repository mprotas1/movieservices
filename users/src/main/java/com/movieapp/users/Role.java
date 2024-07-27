package com.movieapp.users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor @Getter @Setter
final class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "name")
    @Convert(converter = RoleTypeConverter.class)
    private RoleType roleName;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public static Role ofRoleName(RoleType roleType) {
        Role role = new Role();
        role.setRoleName(roleType);
        return role;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", roleName=" + roleName + "]";
    }

}
