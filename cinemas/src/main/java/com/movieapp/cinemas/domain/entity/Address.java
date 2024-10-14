package com.movieapp.cinemas.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Embeddable
public record Address(String street, String city, String postalCode) {

    public Address {
        Assert.notNull(street, "Street must not be null");
        Assert.hasText(street, "Street must not be empty");

        Assert.notNull(city, "City must not be null");
        Assert.hasText(city, "City must not be empty");

        Assert.notNull(postalCode, "Postal code must not be null");
        Assert.hasText(postalCode, "Postal code must not be empty");
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Address address = (Address) other;
        return street.equals(address.street) && city.equals(address.city) && postalCode.equals(address.postalCode);
    }

}
