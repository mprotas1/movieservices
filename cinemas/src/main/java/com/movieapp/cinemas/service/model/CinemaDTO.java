package com.movieapp.cinemas.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;

import java.util.UUID;

public record CinemaDTO(UUID id,
                        String name,
                        @JsonProperty(value = "address") String formattedAddress) {

    public static CinemaDTO fromEntity(Cinema cinema) {
        return new CinemaDTO(cinema.getId().getUuid(), cinema.getName(), getFormattedAddress(cinema.getAddress()));
    }

    private static String getFormattedAddress(Address address) {
        String format = "%s, %s, %s";
        return String.format(format, address.postalCode(), address.city(), address.street());
    }

}
