package com.traininfo.infrastructure.fare;

import com.traininfo.domain.model.FareStatus;
import com.traininfo.domain.model.Journey;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class DemoTicketOfferAdapterTest {

    private final DemoTicketOfferAdapter offerAdapter = new DemoTicketOfferAdapter();

    @Test
    void returnsIllustrativeEuroOffersWithExplicitDemoSource() {
        Journey journey = new Journey("Madrid", "Barcelona", LocalDate.parse("2026-11-15"));

        var offers = offerAdapter.findOffersFor(journey);

        assertThat(offers).hasSize(3)
                .allSatisfy(offer -> {
                    assertThat(offer.source()).isEqualTo("TRAININFO_DEMO_DATA");
                    assertThat(offer.status()).isEqualTo(FareStatus.ILLUSTRATIVE);
                    assertThat(offer.currency().getCurrencyCode()).isEqualTo("EUR");
                });
        assertThat(offers).extracting(offer -> offer.totalPrice().toPlainString())
                .containsExactly("42.50", "58.90", "73.25");
    }
}
