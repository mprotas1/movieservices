package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.model.AddressInformation;
import org.bson.types.ObjectId;

public interface AddressService {
    Address save(AddressInformation addressInformation);
    AddressInformation findById(ObjectId id);
    void deleteById(ObjectId id);
}
