package com.movieapp.cinemas.domain.policy;

class PolishPostalCodePolicy extends PostalCodePolicy {

    @Override
    protected String getPostalCodePattern() {
        return "\\d{2}-\\d{3}";
    }

}
