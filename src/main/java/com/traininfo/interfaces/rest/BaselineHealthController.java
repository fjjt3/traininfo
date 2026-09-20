package com.traininfo.interfaces.rest;

import com.traininfo.domain.port.DatabaseHealthPort;
import com.traininfo.interfaces.rest.dto.HealthResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaselineHealthController {

    private static final String UP = "UP";
    private static final String DOWN = "DOWN";

    private final DatabaseHealthPort databaseHealthPort;

    public BaselineHealthController(DatabaseHealthPort databaseHealthPort) {
        this.databaseHealthPort = databaseHealthPort;
    }

    @GetMapping("/api/v1/health")
    public HealthResponse health() {
        boolean up = databaseHealthPort.isUp();
        return new HealthResponse(serviceStatus(up), serviceStatus(up));
    }

    private String serviceStatus(boolean up) {
        return up ? UP : DOWN;
    }
}