package com.movieapp.cinemas.domain.policy;

class USPostalCodePolicy extends PostalCodePolicy {

    @Override
    protected String getPostalCodePattern() {
        return "\\d{5}(-\\d{4})?";
    }

}
