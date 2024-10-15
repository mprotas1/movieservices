package com.movieapp.cinemas.domain.policy;

import com.movieapp.cinemas.domain.entity.CountryCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostalCodePolicyFactoryTest {

    @Test
    @DisplayName("Create Polish postal code policy")
    void shouldReturnPolishPostalCodePolicyForPLCountryCode() {
        CountryCode countryCode = CountryCode.PL;
        PostalCodePolicy policy = PostalCodePolicyFactory.getPolicy(countryCode);
        assertThat(policy).isInstanceOf(PolishPostalCodePolicy.class);
    }

    @Test
    @DisplayName("Create German postal code policy")
    void shouldReturnGermanPostalCodePolicyForDECountryCode() {
        CountryCode countryCode = CountryCode.DE;
        PostalCodePolicy policy = PostalCodePolicyFactory.getPolicy(countryCode);
        assertThat(policy).isInstanceOf(GermanPostalCodePolicy.class);
    }

    @Test
    @DisplayName("Create US postal code policy")
    void shouldReturnUSPostalCodePolicyForUSCountryCode() {
        CountryCode countryCode = CountryCode.US;
        PostalCodePolicy policy = PostalCodePolicyFactory.getPolicy(countryCode);
        assertThat(policy).isInstanceOf(USPostalCodePolicy.class);
    }

}
