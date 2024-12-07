package com.movieapp.cinemas.domain.policy;

class GermanPostalCodePolicy extends PostalCodePolicy {

    @Override
    public String getPostalCodePattern() {
        return "\\d{5}";
    }

}
