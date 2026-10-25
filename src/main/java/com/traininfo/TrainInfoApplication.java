package com.traininfo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "TrainInfo | Comparador de billetes de tren",
                version = "v1",
                description = "API para comparar ofertas ilustrativas de viajes en tren. "
                        + "Los precios de demostración no son tarifas reales ni comprables."
        )
)
public class TrainInfoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainInfoApplication.class, args);
    }
}