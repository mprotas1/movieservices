package com.movieapp.cinemas.domain.repository;

import com.movieapp.cinemas.domain.entity.Address;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddressRepository extends MongoRepository<Address, ObjectId> {}
