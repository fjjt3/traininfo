package com.traininfo.interfaces.rest.dto;

import com.traininfo.domain.model.FareComparison;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.List;

public record JourneyFareComparisonResponse(
        @Schema(example = "Madrid")
        String origin,
        @Schema(example = "Barcelona")
        String destination,
        @Schema(example = "2026-11-15")
        LocalDate travelDate,
        @Schema(description = "All offers in this response are illustrative demo data, not live, confirmed, or bookable fares.")
        String notice,
        List<TicketOfferResponse> offers
) {

    private static final String DEMO_NOTICE =
            "Illustrative demo offers only. Prices are not live, confirmed, or bookable fares.";

    public static JourneyFareComparisonResponse from(FareComparison comparison) {
        return new JourneyFareComparisonResponse(
                comparison.journey().origin(),
                comparison.journey().destination(),
                comparison.journey().travelDate(),
                DEMO_NOTICE,
                comparison.offers().stream().map(TicketOfferResponse::from).toList()
        );
    }
}
