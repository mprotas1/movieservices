package com.movieapp.cinemas.domain.entity;

public enum SeatType {
    ECONOMY,
    STANDARD,
    VIP;

    public static SeatType fromString(String type) {
        return switch (type) {
            case "ECONOMY" -> ECONOMY;
            case "STANDARD" -> STANDARD;
            case "VIP" -> VIP;
            default -> throw new IllegalArgumentException("Invalid seat type: " + type);
        };
    }

}
