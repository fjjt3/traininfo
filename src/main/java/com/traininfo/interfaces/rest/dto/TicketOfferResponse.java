package com.traininfo.interfaces.rest.dto;

import com.traininfo.domain.model.TicketOffer;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

public record TicketOfferResponse(
        @Schema(example = "Demo operator A")
        String provider,
        @Schema(example = "TRAININFO_DEMO_DATA")
        String source,
        @Schema(example = "42.50")
        BigDecimal totalPrice,
        @Schema(example = "EUR")
        String currency,
        @Schema(description = "This offer is illustrative and is not a live or bookable fare.", example = "ILLUSTRATIVE")
        String status
) {

    public static TicketOfferResponse from(TicketOffer offer) {
        return new TicketOfferResponse(
                offer.provider(),
                offer.source(),
                offer.totalPrice(),
                offer.currency().getCurrencyCode(),
                offer.status().name()
        );
    }
}
