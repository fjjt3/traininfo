package com.traininfo.domain.service;

import com.traininfo.domain.model.FareComparison;
import com.traininfo.domain.model.Journey;
import com.traininfo.domain.model.TicketOffer;
import com.traininfo.domain.port.TicketOfferSourcePort;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class JourneyFareComparisonService {

    private final TicketOfferSourcePort ticketOfferSource;

    public JourneyFareComparisonService(TicketOfferSourcePort ticketOfferSource) {
        this.ticketOfferSource = Objects.requireNonNull(ticketOfferSource, "Ticket offer source is required.");
    }

    public FareComparison compare(Journey journey) {
        Objects.requireNonNull(journey, "Journey is required.");
        List<TicketOffer> offers = ticketOfferSource.findOffersFor(journey).stream()
                .sorted(Comparator.comparing(TicketOffer::totalPrice))
                .toList();
        return new FareComparison(journey, offers);
    }
}
