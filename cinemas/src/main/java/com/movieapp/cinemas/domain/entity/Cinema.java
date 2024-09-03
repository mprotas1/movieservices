package com.movieapp.cinemas.domain.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "cinemas")
@Data
public class Cinema {
    private @MongoId ObjectId id;
    private String name;
    private ObjectId addressId;

    public static Cinema create(String name, Address address) {
        Cinema cinema = new Cinema();
        cinema.setName(name);
        cinema.setAddressId(address.id());
        return cinema;
    }

}
