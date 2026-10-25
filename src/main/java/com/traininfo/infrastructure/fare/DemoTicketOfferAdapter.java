package com.traininfo.infrastructure.fare;

import com.traininfo.domain.model.FareStatus;
import com.traininfo.domain.model.Journey;
import com.traininfo.domain.model.TicketOffer;
import com.traininfo.domain.port.TicketOfferSourcePort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

@Component
public class DemoTicketOfferAdapter implements TicketOfferSourcePort {

    private static final Currency EUR = Currency.getInstance("EUR");
    private static final String DEMO_SOURCE = "TRAININFO_DEMO_DATA";

    @Override
    public List<TicketOffer> findOffersFor(Journey journey) {
        return List.of(
                illustrativeOffer("Demo operator A", "42.50"),
                illustrativeOffer("Demo operator B", "58.90"),
                illustrativeOffer("Demo operator C", "73.25")
        );
    }

    private TicketOffer illustrativeOffer(String provider, String price) {
        return new TicketOffer(
                provider,
                DEMO_SOURCE,
                new BigDecimal(price),
                EUR,
                FareStatus.ILLUSTRATIVE
        );
    }
}
