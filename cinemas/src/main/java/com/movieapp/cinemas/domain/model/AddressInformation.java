package com.movieapp.cinemas.domain.model;

import com.movieapp.cinemas.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressInformation(@NotBlank String street,
                                 @NotBlank String city,
                                 @NotBlank String postalCode) {

    public static AddressInformation fromEntity(Address address) {
        return new AddressInformation(address.getStreet(), address.getCity(), address.getPostalCode());
    }

}
