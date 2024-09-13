package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.model.AddressInformation;
import jakarta.validation.Valid;

public interface AddressService {
    Address save(@Valid AddressInformation addressInformation);
    AddressInformation findById(Long id);
    void deleteById(Long id);
    boolean addressExists(AddressInformation addressInformation);
}
