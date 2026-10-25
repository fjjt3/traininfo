package com.traininfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers(disabledWithoutDocker = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProjectSetupIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("traininfo")
            .withUsername("traininfo_user")
            .withPassword("traininfo_password");

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void applicationContextLoadsAndDatabaseIsReachable() {
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertThat(result).isEqualTo(1);
    }

    @Test
    void healthEndpointReportsServiceAndDatabaseUp() throws Exception {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/v1/health", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        String body = objectMapper.readTree(response.getBody()).toString();
        assertThat(body).contains("\"status\":\"UP\"", "\"database\":\"UP\"");
    }

    @Test
    void openApiDocumentsJourneyFareComparisonEndpoint() {
        ResponseEntity<String> response = restTemplate.getForEntity("/v3/api-docs", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody())
                .contains("/api/v1/journey-fare-comparisons")
                .contains("TRAININFO_DEMO_DATA")
                .contains("ILLUSTRATIVE");
    }

    @Test
    void journeyFareComparisonEndpointReturnsDemoOffers() throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("""
                {
                  "origin": "Madrid",
                  "destination": "Barcelona",
                  "travelDate": "2026-11-15"
                }
                """, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(
                "/api/v1/journey-fare-comparisons",
                request,
                String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        var responseBody = objectMapper.readTree(response.getBody());
        assertThat(responseBody.path("offers")).hasSize(3);
        assertThat(responseBody.path("offers").findValuesAsText("status"))
                .containsOnly("ILLUSTRATIVE");
        assertThat(responseBody.path("offers").get(0).path("totalPrice").decimalValue())
                .isEqualByComparingTo("42.50");
        assertThat(responseBody.path("offers").get(1).path("totalPrice").decimalValue())
                .isEqualByComparingTo("58.90");
        assertThat(responseBody.path("offers").get(2).path("totalPrice").decimalValue())
                .isEqualByComparingTo("73.25");
    }
}