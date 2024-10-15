package com.movieapp.cinemas.domain.policy;

import com.movieapp.cinemas.domain.entity.CountryCode;

public class PostalCodePolicyFactory {

    public static PostalCodePolicy getPolicy(CountryCode countryCode) {
        if (countryCode.equals(CountryCode.PL)) {
            return new PolishPostalCodePolicy();
        }
        else if(countryCode.equals(CountryCode.US)) {
            return new USPostalCodePolicy();
        }
        else if(countryCode.equals(CountryCode.DE)) {
            return new GermanPostalCodePolicy();
        }
        throw new IllegalArgumentException("Unsupported country code: " + countryCode);
    }

}
