package com.traininfo.domain.service;

import com.traininfo.domain.model.FareStatus;
import com.traininfo.domain.model.Journey;
import com.traininfo.domain.model.TicketOffer;
import com.traininfo.domain.port.TicketOfferSourcePort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JourneyFareComparisonServiceTest {

    private final TicketOfferSourcePort ticketOfferSource = mock(TicketOfferSourcePort.class);
    private final JourneyFareComparisonService comparisonService =
            new JourneyFareComparisonService(ticketOfferSource);

    @Test
    void returnsOffersOrderedByTotalPrice() {
        Journey journey = new Journey("Madrid", "Barcelona", LocalDate.parse("2026-11-15"));
        TicketOffer expensiveOffer = offer("75.00");
        TicketOffer inexpensiveOffer = offer("42.50");
        when(ticketOfferSource.findOffersFor(journey)).thenReturn(List.of(expensiveOffer, inexpensiveOffer));

        var comparison = comparisonService.compare(journey);

        assertThat(comparison.offers()).containsExactly(inexpensiveOffer, expensiveOffer);
    }

    @Test
    void returnsEmptyOfferListWhenSourceHasNoOffers() {
        Journey journey = new Journey("Madrid", "Barcelona", LocalDate.parse("2026-11-15"));
        when(ticketOfferSource.findOffersFor(journey)).thenReturn(List.of());

        assertThat(comparisonService.compare(journey).offers()).isEmpty();
    }

    private TicketOffer offer(String price) {
        return new TicketOffer(
                "Demo operator",
                "TRAININFO_DEMO_DATA",
                new BigDecimal(price),
                Currency.getInstance("EUR"),
                FareStatus.ILLUSTRATIVE
        );
    }
}
