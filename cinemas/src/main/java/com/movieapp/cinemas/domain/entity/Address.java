package com.movieapp.cinemas.domain.entity;

import com.movieapp.cinemas.domain.model.AddressInformation;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "addresses")
public record Address(@MongoId ObjectId id,
                      String street,
                      String city,
                      String postalCode,
                      ObjectId cinemaId) {

    public static Address create(AddressInformation information) {
        return new Address(new ObjectId(), information.street(), information.city(), information.postalCode(), null);
    }

}
