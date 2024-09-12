package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.model.AddressInformation;

public interface AddressService {
    Address save(AddressInformation addressInformation);
    AddressInformation findById(Long id);
    void deleteById(Long id);
}
