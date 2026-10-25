package com.traininfo.interfaces.rest.dto;

import com.traininfo.domain.model.Journey;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record JourneyFareComparisonRequest(
        @NotBlank
        @Schema(description = "Journey origin, entered as free text.", example = "Madrid")
        String origin,
        @NotBlank
        @Schema(description = "Journey destination, entered as free text.", example = "Barcelona")
        String destination,
        @NotNull
        @Schema(description = "Travel date in ISO-8601 format.", example = "2026-11-15")
        LocalDate travelDate
) {

    public Journey toJourney() {
        return new Journey(origin, destination, travelDate);
    }
}
