package com.movieapp.cinemas.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.entity.Cinema;

public record CinemaDTO(Long id,
                        String name,
                        @JsonProperty(value = "address") String formattedAddress) {

    public static CinemaDTO fromEntity(Cinema cinema) {
        return new CinemaDTO(cinema.getId(), cinema.getName(), getFormattedAddress(cinema.getAddress()));
    }

    private static String getFormattedAddress(Address address) {
        String format = "%s %s, %s";
        return String.format(format, address.getPostalCode(), address.getCity(), address.getStreet());
    }

}
