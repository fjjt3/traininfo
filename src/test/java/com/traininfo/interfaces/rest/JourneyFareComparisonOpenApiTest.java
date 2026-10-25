package com.traininfo.interfaces.rest;

import com.traininfo.TrainInfoApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        classes = TrainInfoApplication.class,
        properties = {
                "spring.autoconfigure.exclude="
                        + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
                        + "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration,"
                        + "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
        }
)
@AutoConfigureMockMvc
class JourneyFareComparisonOpenApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void documentsComparisonRequestAndResponseExamples() throws Exception {
        mockMvc.perform(get("/v3/api-docs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.info.title").value("TrainInfo | Comparador de billetes de tren"))
                .andExpect(jsonPath("$.info.description").value(
                        "API para comparar ofertas ilustrativas de viajes en tren. "
                                + "Los precios de demostración no son tarifas reales ni comprables."
                ))
                .andExpect(jsonPath("$.paths['/api/v1/journey-fare-comparisons'].post").exists())
                .andExpect(jsonPath("$.paths['/api/v1/journey-fare-comparisons'].post.tags[0]")
                        .value("Comparador de billetes"))
                .andExpect(jsonPath("$.paths['/api/v1/journey-fare-comparisons'].post.summary")
                        .value("Comparar ofertas de billetes"))
                .andExpect(jsonPath("$.paths['/api/v1/journey-fare-comparisons'].post.requestBody.content['application/json'].example.origin")
                        .value("Madrid"))
                .andExpect(jsonPath("$.paths['/api/v1/journey-fare-comparisons'].post.responses['200'].content['application/json'].example.offers[0].status")
                        .value("ILLUSTRATIVE"));
    }
}
