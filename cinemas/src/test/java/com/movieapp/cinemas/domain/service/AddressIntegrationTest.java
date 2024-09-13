package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.repository.AddressRepository;
import com.movieapp.cinemas.testcontainers.Containers;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AddressIntegrationTest extends Containers {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AddressRepository addressRepository;

    @Test
    void shouldCheckIfAddressExists() {
        AddressInformation addressInformation = new AddressInformation("Blank Street", "Blank City", "00-000");
        Address address = Address.create(addressInformation);
        addressRepository.save(address);
        boolean result = addressService.addressExists(addressInformation);
        assertTrue(result);
    }

    @Test
    void shouldVerifyAddressDoesNotExist() {
        addressRepository.deleteAll();
        AddressInformation addressInformation = new AddressInformation("Blank Street", "Blank City", "00-000");
        boolean result = addressService.addressExists(addressInformation);
        assertFalse(result);
    }

    @Test
    void shouldFindAddressById() {
        AddressInformation addressInformation = new AddressInformation("Blank Street", "Blank City", "00-000");
        Address address = Address.create(addressInformation);
        addressRepository.save(address);
        AddressInformation foundAddress = addressService.findById(address.getId());
        assertEquals(foundAddress, addressInformation);
    }

    @Test
    void shouldNotFindAddressById() {
        assertThrows(EntityNotFoundException.class, () -> addressService.findById(1L));
    }



}
