package com.movieapp.cinemas.domain.entity;

import com.movieapp.cinemas.domain.model.AddressInformation;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "addresses") @Data @NoArgsConstructor
public class Address {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String street;
    private String city;
    private String postalCode;

    public Address(Long id, String street, String city, String postalCode) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
    }

    public static Address create(AddressInformation information) {
        return new Address(null, information.street(), information.city(), information.postalCode());
    }

}
