package com.movieapp.cinemas.domain.policy;

import com.movieapp.cinemas.domain.entity.CountryCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PostalCodePolicyFactoryTest {

    @Test
    void shouldReturnPolishPostalCodePolicyForPLCountryCode() {
        CountryCode countryCode = CountryCode.PL;
        PostalCodePolicy policy = PostalCodePolicyFactory.getPolicy(countryCode);
        assertThat(policy).isInstanceOf(PolishPostalCodePolicy.class);
    }

    @Test
    void shouldReturnGermanPostalCodePolicyForDECountryCode() {
        CountryCode countryCode = CountryCode.DE;
        PostalCodePolicy policy = PostalCodePolicyFactory.getPolicy(countryCode);
        assertThat(policy).isInstanceOf(GermanPostalCodePolicy.class);
    }

    @Test
    void shouldReturnUSPostalCodePolicyForUSCountryCode() {
        CountryCode countryCode = CountryCode.US;
        PostalCodePolicy policy = PostalCodePolicyFactory.getPolicy(countryCode);
        assertThat(policy).isInstanceOf(USPostalCodePolicy.class);
    }

}
