package com.movieapp.users.domain.entity;

import java.util.Arrays;

public enum RoleType {
    USER,
    MODERATOR,
    ADMIN;

    @Override
    public String toString() {
        return getRoleTypeName();
    }

    public String getRoleTypeName() {
        return "ROLE_" + this.name();
    }

    public static RoleType fromString(String value) {
        return Arrays.stream(values())
                .filter(v -> v.getRoleTypeName()
                        .equals(value))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
