# traininfo

Backend platform to query, aggregate, and serve train schedules and route information for rail services.

## Tech Stack

- **Java 25** (Records, Pattern Matching, Sealed Classes, Virtual Threads)
- **Spring Boot 3.5.x** with Spring Web, Spring Data JPA, Actuator, Validation
- **OpenAPI 3 / Swagger UI** via springdoc-openapi
- **PostgreSQL 16** running in Docker Compose
- **Flyway** for versioned database migrations
- **Testing:** JUnit 5, AssertJ, Mockito, Testcontainers, ArchUnit

## Architecture

Three-layer hexagonal layout under `com.traininfo`:

- `domain` — Pure business logic and port interfaces (zero framework dependencies).
- `infrastructure` — Persistence adapters, JPA interfaces, and external adapters.
- `interfaces` — REST controllers and request/response DTOs.

## Getting Started

### Prerequisites

- Java 25 (via SDKMAN: `sdk install java 25.0.x-tem`)
- Docker Desktop running

### Run

```bash
docker compose up -d            # Start PostgreSQL
./mvnw spring-boot:run          # Start the application (http://localhost:8080)
```

### Verify

```bash
curl http://localhost:8080/api/v1/health   # {"status":"UP","database":"UP"}
curl http://localhost:8080/actuator/health
```

### API docs (Swagger UI)

- UI: http://localhost:8080/swagger-ui/index.html
- OpenAPI spec: http://localhost:8080/v3/api-docs

### Tests

```bash
./mvnw test      # Unit + architecture tests
./mvnw verify    # Includes Testcontainers integration tests (needs Docker)
```

## Database

| Property          | Value        |
|-------------------|--------------|
| Host              | localhost    |
| Port              | 5432         |
| Database          | traininfo    |
| User              | traininfo_user |
| Password          | traininfo_password |

Schema is owned by Flyway; migrations live in `src/main/resources/db/migration/`.