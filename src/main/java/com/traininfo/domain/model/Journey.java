package com.traininfo.domain.model;

import com.traininfo.domain.exception.InvalidJourneyException;

import java.time.LocalDate;
import java.util.Objects;

public record Journey(String origin, String destination, LocalDate travelDate) {

    public Journey {
        origin = requireStationName(origin, "Origin");
        destination = requireStationName(destination, "Destination");
        Objects.requireNonNull(travelDate, "Travel date is required.");

        if (origin.equalsIgnoreCase(destination)) {
            throw new InvalidJourneyException("Origin and destination must be different.");
        }
    }

    private static String requireStationName(String stationName, String fieldName) {
        Objects.requireNonNull(stationName, fieldName + " is required.");
        String normalizedName = stationName.trim();
        if (normalizedName.isEmpty()) {
            throw new InvalidJourneyException(fieldName + " must not be blank.");
        }
        return normalizedName;
    }
}
