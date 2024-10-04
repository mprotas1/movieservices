package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
public record Address(String street, String city, String postalCode) {

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Address address = (Address) other;
        return street.equals(address.street) && city.equals(address.city) && postalCode.equals(address.postalCode);
    }

}
