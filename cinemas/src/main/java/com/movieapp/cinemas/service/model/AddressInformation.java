package com.movieapp.cinemas.service.model;

import com.movieapp.cinemas.domain.entity.Address;
import jakarta.validation.constraints.NotBlank;

public record AddressInformation(@NotBlank String street,
                                 @NotBlank String city,
                                 @NotBlank String postalCode) {

    public static AddressInformation fromEntity(Address address) {
        return new AddressInformation(address.street(), address.city(), address.postalCode());
    }

    public static Address toEntity(AddressInformation addressInformation) {
        return new Address(addressInformation.city(), addressInformation.street(), addressInformation.postalCode());
    }

}
