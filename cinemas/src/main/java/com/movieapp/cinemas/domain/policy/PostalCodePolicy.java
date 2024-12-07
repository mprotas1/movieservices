package com.movieapp.cinemas.domain.policy;

public abstract class PostalCodePolicy {
    PostalCodePolicy() {}

    public boolean isValid(String postalCode) {
        validatePostalCode(postalCode);
        String pattern = getPostalCodePattern();
        return postalCode.matches(pattern);
    }

    public void validatePostalCode(String postalCode) {
        if(postalCode == null || postalCode.isBlank()) {
            throw new IllegalArgumentException("Postal code must not be null or empty");
        }
    }

    protected abstract String getPostalCodePattern();

}
