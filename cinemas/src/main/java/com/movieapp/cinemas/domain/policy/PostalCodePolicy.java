package com.movieapp.cinemas.domain.policy;

public abstract class PostalCodePolicy {
    PostalCodePolicy() {}

    public boolean isValid(String postalCode) {
        String pattern = getPostalCodePattern();
        return postalCode.matches(pattern);
    }

    protected abstract String getPostalCodePattern();
}
