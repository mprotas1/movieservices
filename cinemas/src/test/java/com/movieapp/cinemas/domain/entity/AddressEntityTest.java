package com.movieapp.cinemas.domain.entity;

import com.movieapp.cinemas.service.model.AddressInformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AddressEntityTest {

    @ParameterizedTest
    @DisplayName("Should create Address with valid data")
    @MethodSource("getValidAddresses")
    void shouldCreateAddressWithValidData(AddressInformation address) {
        assertDoesNotThrow(() -> new Address(address.street(), address.city(), address.postalCode(), address.countryCode()));
    }

    @ParameterizedTest
    @DisplayName("Should reject creating Address with invalid data")
    @MethodSource("getInvalidAddresses")
    void shouldNotCreateAddressWithInvalidData(AddressInformation address) {
        assertThrows(IllegalArgumentException.class, () -> new Address(address.street(), address.city(), address.postalCode(), address.countryCode()));
    }

    private static Stream<AddressInformation> getValidAddresses() {
        return Stream.of(
            new AddressInformation("Street", "City", "00-000", CountryCode.PL),
            new AddressInformation("Jana Kazimierza", "Kraków", "55-555", CountryCode.PL),
            new AddressInformation("Wyzwolenia", "Szczecin", "70-001", CountryCode.PL),
            new AddressInformation("Poznańska", "Poznań", "51-312", CountryCode.PL)
        );
    }

    private static Stream<AddressInformation> getInvalidAddresses() {
        return Stream.of(
            new AddressInformation(null, "City", "00-000", CountryCode.PL),
            new AddressInformation("Street", null, "00-000", CountryCode.PL),
            new AddressInformation("Street", "City", null, CountryCode.PL),
            new AddressInformation("Street", "City", "00-000", null),
            new AddressInformation("", "City", "00-000", CountryCode.PL)
        );
    }

}
