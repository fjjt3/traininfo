package com.traininfo.interfaces.rest;

import com.traininfo.domain.service.JourneyFareComparisonService;
import com.traininfo.interfaces.rest.dto.JourneyFareComparisonRequest;
import com.traininfo.interfaces.rest.dto.JourneyFareComparisonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/journey-fare-comparisons")
@Tag(name = "Comparador de billetes", description = "Comparación de ofertas demostrativas de viajes en tren.")
public class JourneyFareComparisonController {

    private final JourneyFareComparisonService comparisonService;

    public JourneyFareComparisonController(JourneyFareComparisonService comparisonService) {
        this.comparisonService = comparisonService;
    }

    @PostMapping
    @Operation(
            summary = "Comparar ofertas de billetes",
            description = "Compara ofertas demostrativas para un adulto usando origen, destino y fecha de viaje. "
                    + "Los precios son ilustrativos; no son tarifas reales, confirmadas ni comprables.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JourneyFareComparisonRequest.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "origin": "Madrid",
                                      "destination": "Barcelona",
                                      "travelDate": "2026-11-15"
                                    }
                                    """)
                    )
            )
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ofertas demostrativas ordenadas por precio total.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JourneyFareComparisonResponse.class),
                            examples = @ExampleObject(value = """
                                    {
                                      "origin": "Madrid",
                                      "destination": "Barcelona",
                                      "travelDate": "2026-11-15",
                                      "notice": "Illustrative demo offers only. Prices are not live, confirmed, or bookable fares.",
                                      "offers": [
                                        {
                                          "provider": "Demo operator A",
                                          "source": "TRAININFO_DEMO_DATA",
                                          "totalPrice": 42.50,
                                          "currency": "EUR",
                                          "status": "ILLUSTRATIVE"
                                        }
                                      ]
                                    }
                                    """)
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "La solicitud no es válida o no se puede interpretar.",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = ProblemDetail.class)
                    )
            )
    })
    public JourneyFareComparisonResponse compare(@Valid @RequestBody JourneyFareComparisonRequest request) {
        return JourneyFareComparisonResponse.from(comparisonService.compare(request.toJourney()));
    }
}
