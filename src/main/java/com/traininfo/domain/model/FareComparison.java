package com.traininfo.domain.model;

import java.util.List;
import java.util.Objects;

public record FareComparison(Journey journey, List<TicketOffer> offers) {

    public FareComparison {
        Objects.requireNonNull(journey, "Journey is required.");
        offers = List.copyOf(Objects.requireNonNull(offers, "Offers are required."));
    }
}
