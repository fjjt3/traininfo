package com.traininfo.interfaces.rest;

import com.traininfo.domain.model.FareComparison;
import com.traininfo.domain.model.FareStatus;
import com.traininfo.domain.model.Journey;
import com.traininfo.domain.model.TicketOffer;
import com.traininfo.domain.service.JourneyFareComparisonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(JourneyFareComparisonController.class)
class JourneyFareComparisonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private JourneyFareComparisonService comparisonService;

    @Test
    void returnsClearlyLabeledDemoOffers() throws Exception {
        Journey journey = new Journey("Madrid", "Barcelona", LocalDate.parse("2026-11-15"));
        TicketOffer offer = new TicketOffer(
                "Demo operator A",
                "TRAININFO_DEMO_DATA",
                new BigDecimal("42.50"),
                Currency.getInstance("EUR"),
                FareStatus.ILLUSTRATIVE
        );
        when(comparisonService.compare(journey)).thenReturn(new FareComparison(journey, List.of(offer)));

        mockMvc.perform(post("/api/v1/journey-fare-comparisons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "Madrid",
                                  "destination": "Barcelona",
                                  "travelDate": "2026-11-15"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.origin").value("Madrid"))
                .andExpect(jsonPath("$.destination").value("Barcelona"))
                .andExpect(jsonPath("$.travelDate").value("2026-11-15"))
                .andExpect(jsonPath("$.offers[0].source").value("TRAININFO_DEMO_DATA"))
                .andExpect(jsonPath("$.offers[0].status").value("ILLUSTRATIVE"))
                .andExpect(jsonPath("$.offers[0].currency").value("EUR"))
                .andExpect(jsonPath("$.notice").value(
                        "Illustrative demo offers only. Prices are not live, confirmed, or bookable fares."
                ));

        verify(comparisonService).compare(journey);
    }

    @Test
    void rejectsBlankOriginWithProblemDetails() throws Exception {
        mockMvc.perform(post("/api/v1/journey-fare-comparisons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": " ",
                                  "destination": "Barcelona",
                                  "travelDate": "2026-11-15"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").exists())
                .andExpect(jsonPath("$.errors[0]").value("origin: must not be blank"));
    }

    @Test
    void rejectsMissingJourneyFields() throws Exception {
        mockMvc.perform(post("/api/v1/journey-fare-comparisons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "Madrid"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors.length()").value(2));
    }

    @Test
    void rejectsSameOriginAndDestination() throws Exception {
        mockMvc.perform(post("/api/v1/journey-fare-comparisons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "Madrid",
                                  "destination": " madrid ",
                                  "travelDate": "2026-11-15"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Origin and destination must be different."));
    }

    @Test
    void returnsEmptyOffersWhenNoDemoOfferIsAvailable() throws Exception {
        Journey journey = new Journey("Madrid", "Barcelona", LocalDate.parse("2026-11-15"));
        when(comparisonService.compare(journey)).thenReturn(new FareComparison(journey, List.of()));

        mockMvc.perform(post("/api/v1/journey-fare-comparisons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "Madrid",
                                  "destination": "Barcelona",
                                  "travelDate": "2026-11-15"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.offers").isEmpty());
    }

    @Test
    void rejectsNonIsoTravelDate() throws Exception {
        mockMvc.perform(post("/api/v1/journey-fare-comparisons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "origin": "Madrid",
                                  "destination": "Barcelona",
                                  "travelDate": "15/11/2026"
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value(
                        "Request body is invalid or contains an unsupported value format. Dates must use ISO-8601."
                ));
    }

}
