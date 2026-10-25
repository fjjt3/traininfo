package com.traininfo.infrastructure.fare;

import com.traininfo.domain.port.TicketOfferSourcePort;
import com.traininfo.domain.service.JourneyFareComparisonService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JourneyFareComparisonConfiguration {

    @Bean
    JourneyFareComparisonService journeyFareComparisonService(TicketOfferSourcePort ticketOfferSource) {
        return new JourneyFareComparisonService(ticketOfferSource);
    }
}
