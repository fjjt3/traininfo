package com.traininfo.domain.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Objects;

public record TicketOffer(
        String provider,
        String source,
        BigDecimal totalPrice,
        Currency currency,
        FareStatus status
) {

    public TicketOffer {
        Objects.requireNonNull(provider, "Provider is required.");
        Objects.requireNonNull(source, "Source is required.");
        Objects.requireNonNull(totalPrice, "Total price is required.");
        Objects.requireNonNull(currency, "Currency is required.");
        Objects.requireNonNull(status, "Fare status is required.");

        if (provider.isBlank() || source.isBlank()) {
            throw new IllegalArgumentException("Provider and source must not be blank.");
        }
        if (totalPrice.signum() < 0) {
            throw new IllegalArgumentException("Total price must not be negative.");
        }
    }
}
