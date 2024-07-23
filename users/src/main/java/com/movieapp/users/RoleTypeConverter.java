package com.movieapp.users;

import jakarta.persistence.AttributeConverter;

public class RoleTypeConverter implements AttributeConverter<RoleType, String> {

    @Override
    public String convertToDatabaseColumn(RoleType roleType) {
        return roleType.getRoleTypeName();
    }

    @Override
    public RoleType convertToEntityAttribute(String s) {
        return RoleType.fromString(s);
    }

}
