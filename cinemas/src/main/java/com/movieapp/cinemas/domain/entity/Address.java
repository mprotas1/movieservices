package com.movieapp.cinemas.domain.entity;

import com.movieapp.cinemas.domain.policy.PostalCodePolicy;
import com.movieapp.cinemas.domain.policy.PostalCodePolicyFactory;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.util.Assert;

@Embeddable
public record Address(String street,
                      String city,
                      String postalCode,
                      @Enumerated(EnumType.STRING) CountryCode countryCode) {

    public Address {
        Assert.notNull(street, "Street must not be null");
        Assert.hasText(street, "Street must not be empty");
        Assert.notNull(city, "City must not be null");
        Assert.hasText(city, "City must not be empty");
        Assert.notNull(countryCode, "Country code must not be null");
        Assert.isTrue(hasValidPostalCode(postalCode, countryCode), "Postal code must be valid");
    }

    private boolean hasValidPostalCode(String postalCode, CountryCode countryCode) {
        PostalCodePolicy postalCodePolicy = PostalCodePolicyFactory.getPolicy(countryCode);
        return postalCodePolicy.isValid(postalCode);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Address address = (Address) other;
        return street.equals(address.street) && city.equals(address.city) && postalCode.equals(address.postalCode);
    }

}
