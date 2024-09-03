package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.exception.DocumentNotFoundException;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressLocationService implements AddressService {
    private final AddressRepository addressRepository;

    @Override
    public AddressInformation findById(ObjectId id) {
        return addressRepository.findById(id)
                .map(AddressInformation::fromEntity)
                .orElseThrow(() -> new DocumentNotFoundException("Did not find address with id: " + id));
    }

    @Override
    public Address save(AddressInformation addressInformation) {
        return addressRepository.save(Address.create(addressInformation));
    }

    @Override
    public void deleteById(ObjectId id) {
    }


}