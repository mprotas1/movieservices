package com.movieapp.cinemas.domain.policy;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(OrderAnnotation.class)
class PostalCodePolicyTest {
    private PostalCodePolicy polishPostalCodePolicy;
    private PostalCodePolicy germanPostalCodePolicy;
    private PostalCodePolicy usPostalCodePolicy;

    @BeforeEach
    void setUp() {
        polishPostalCodePolicy = new PolishPostalCodePolicy();
        germanPostalCodePolicy = new GermanPostalCodePolicy();
        usPostalCodePolicy = new USPostalCodePolicy();
    }

    @ParameterizedTest
    @CsvSource(value = {"72-300", "00-000", "01-234", "99-999", "00-001"})
    @DisplayName("PL postal code - positive cases")
    @Order(1)
    void shouldValidatePolishPostalCode(String postalCode) {
        boolean isValid = polishPostalCodePolicy.isValid(postalCode);
        assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource(value = {"124", "31231", "124141", "AVSDV", "invalidPostalCode"})
    @DisplayName("PL postal code - negative cases")
    @Order(2)
    void shouldRejectInvalidPolishPostalCode(String postalCode) {
        boolean isValid = polishPostalCodePolicy.isValid(postalCode);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @CsvSource(value = {"12345", "90210", "10001", "33109", "94105"})
    @DisplayName("US postal code - positive cases")
    @Order(3)
    void shouldValidateUSPostalCode(String postalCode) {
        boolean isValid = usPostalCodePolicy.isValid(postalCode);
        assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource(value = {"1234", "ABCDE", "123456", "12-3456", "invalid"})
    @DisplayName("US postal code - negative cases")
    @Order(4)
    void shouldRejectInvalidUSPostalCode(String postalCode) {
        boolean isValid = usPostalCodePolicy.isValid(postalCode);
        assertFalse(isValid);
    }

    @ParameterizedTest
    @CsvSource(value = {"12345", "90210", "10001", "33109", "94105"})
    @DisplayName("DE postal code - positive cases")
    @Order(5)
    void shouldValidateDEPostalCode(String postalCode) {
        boolean isValid = germanPostalCodePolicy.isValid(postalCode);
        assertTrue(isValid);
    }

    @ParameterizedTest
    @CsvSource(value = {"1234X", "ABCDE", "123456", "12-3456", "invalid"})
    @DisplayName("DE postal code - negative cases")
    @Order(6)
    void shouldRejectInvalidDEPostalCode(String postalCode) {
        boolean isValid = germanPostalCodePolicy.isValid(postalCode);
        assertFalse(isValid);
    }

}
