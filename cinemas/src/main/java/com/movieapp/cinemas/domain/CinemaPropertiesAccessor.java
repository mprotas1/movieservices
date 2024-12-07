package com.movieapp.cinemas.domain;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CinemaPropertiesAccessor {
    private final String CINEMA_PROPERTIES_FILE = "cinema.properties";
    private final String CINEMA_ROOM_SEAT_PER_ROW = "room.seat-per-row";

    public int getSeatPerRowFromProperties() {
        Properties properties = getCinemaProperties();
        return Integer.parseInt(properties.getProperty(CINEMA_ROOM_SEAT_PER_ROW));
    }

    private Properties getCinemaProperties() {
        Properties properties = new Properties();
        try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CINEMA_PROPERTIES_FILE)) {
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Error reading application.properties file", e);
        }
    }

}
