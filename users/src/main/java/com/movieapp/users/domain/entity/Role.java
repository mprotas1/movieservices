package com.movieapp.users.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.movieapp.users.domain.RoleTypeConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@NoArgsConstructor @Getter @Setter
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, name = "role_type")
    @Convert(converter = RoleTypeConverter.class)
    private RoleType roleType;

    @ManyToMany(fetch = FetchType.LAZY, targetEntity = User.class)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private List<User> users = new ArrayList<>();

    public static Role ofRoleType(RoleType roleType) {
        Role role = new Role();
        role.setRoleType(roleType);
        return role;
    }

    @Override
    public String toString() {
        return "[id=" + id + ", roleName=" + roleType + "]";
    }

}
