package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.exception.DocumentNotFoundException;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddressLocationService implements AddressService {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final AddressRepository addressRepository;

    @Override
    public AddressInformation findById(Long id) {
        log.debug("Finding address with id: {}", id);
        return addressRepository.findById(id)
                .map(AddressInformation::fromEntity)
                .orElseThrow(() -> new DocumentNotFoundException("Did not find address with id: " + id));
    }

    @Override
    public Address save(AddressInformation addressInformation) {
        log.debug("Saving address: {}", addressInformation);
        return addressRepository.save(Address.create(addressInformation));
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting address with id: {}", id);
        addressRepository.deleteById(id);
    }

}
