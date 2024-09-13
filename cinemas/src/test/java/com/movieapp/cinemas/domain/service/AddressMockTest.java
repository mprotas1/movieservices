package com.movieapp.cinemas.domain.service;

import com.movieapp.cinemas.domain.entity.Address;
import com.movieapp.cinemas.domain.model.AddressInformation;
import com.movieapp.cinemas.domain.repository.AddressRepository;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AddressMockTest {
    @InjectMocks
    private AddressLocationService addressService;
    @Mock
    private AddressRepository addressRepository;

    private final AddressInformation exampleAddressInformation = new AddressInformation("Al. Wyzwolenia", "Szczecin", "71-210");

    @Test
    void shouldCreateAddressWithValidData() {
        Address address = Address.create(exampleAddressInformation);
        address.setId(1L);
        when(addressRepository.exists(any())).thenReturn(false);
        when(addressRepository.save(any())).thenReturn(address);

        Address created = addressService.save(exampleAddressInformation);

        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(exampleAddressInformation.street(), created.getStreet());
        assertEquals(exampleAddressInformation.city(), created.getCity());
        assertEquals(exampleAddressInformation.postalCode(), created.getPostalCode());
    }

    @Test
    void shouldNotCreateExistingAddress() {
        when(addressRepository.exists(any())).thenReturn(true);
        assertThrows(EntityExistsException.class, () -> addressService.save(exampleAddressInformation));
    }

    @Test
    void shouldFindAddressById() {
        Long id = 1L;
        Address address = Address.create(exampleAddressInformation);
        address.setId(id);
        when(addressRepository.findById(id)).thenReturn(java.util.Optional.of(address));
        AddressInformation addressInformation = addressService.findById(id);
        assertNotNull(addressInformation);
        assertEquals(exampleAddressInformation.street(), addressInformation.street());
        assertEquals(exampleAddressInformation.city(), addressInformation.city());
        assertEquals(exampleAddressInformation.postalCode(), addressInformation.postalCode());
    }

}
