# Tech Stack & Conventions

_How the project is built and the rules that all code must follow. This technical reference must not be violated by any feature plan._

## Technologies

- **Language:** Java 25 (utilizing Records, Pattern Matching, Sealed Classes, and Virtual Threads).
- **Framework / Runtime:** Spring Boot 3.x with Java 25 compatibility.
- **Database:** PostgreSQL 16+ (running in Docker container).
- **Migration Tool:** Flyway for version-controlled database schema migrations.
- **Containerization:** Docker & Docker Compose.
- **Testing:** JUnit 5, AssertJ, Mockito, Testcontainers (PostgreSQL), and ArchUnit.

## Key Files & Modules

- `docker-compose.yml` — Local development infrastructure (PostgreSQL container, environment configuration).
- `Dockerfile` — Multi-stage Docker build for the Java 25 application backend.
- `src/main/java/com/traininfo/domain/` — Pure domain model, records, entities, and port interfaces (zero framework dependencies).
- `src/main/java/com/traininfo/application/` — Use cases and service orchestration.
- `src/main/java/com/traininfo/infrastructure/` — Database persistence adapters, external API clients, and Spring REST controllers.
- `src/main/resources/db/migration/` — Flyway SQL migration scripts.
- `features/` — SDD specification, planning, and task files for each feature.

## Commands

- `docker compose up -d` — Starts local PostgreSQL database container.
- `./gradlew bootRun` / `./mvnw spring-boot:run` — Starts the Spring Boot application locally.
- `./gradlew test` / `./mvnw test` — Runs unit and integration test suites.
- `./gradlew check` / `./mvnw verify` — Runs static code analysis and ArchUnit architecture verification.
- `docker build -t traininfo-backend .` — Builds the container image.

## Data Model / Domain

- `Station` — Represents a train station (`id`, `adifCode`, `name`, `latitude`, `longitude`).
- `TrainService` — Details of a train service (`trainNumber`, `serviceType` [e.g., AVE, Cercanias, MD], `operator`).
- `Schedule` — Station arrival and departure timing (`arrivalTime`, `departureTime`, `platform`).
- `Route` — Ordered collection of station stops for a specific train service.

## Conventions

- **Naming:** `camelCase` for variables/methods, `PascalCase` for classes/interfaces/records, `SNAKE_CASE` for constants.
- **Modern Java Practices:** Prefer `records` for immutable DTOs and value objects; use `Optional` for nullable values.
- **Test Placement:** Mirrored under `src/test/java/`, suffixed with `Test` (unit) or `IT` (integration).
- **Error Handling:** Domain-specific exceptions mapped to RFC 7807 Problem Details HTTP responses in the infrastructure layer.

## Hard Rules

- **Zero Framework Imports in Domain:** Classes in `domain` must NEVER import `org.springframework.*`, `jakarta.persistence.*`, `com.fasterxml.jackson.*`, or any external library.
- **No `null` Passing or Returning:** Explicitly forbidden. Use `Optional` or empty collections.
- **SDD Enforcement:** No pull requests or production code without an approved `features/NNN-feature-name/` directory containing `spec.md`, `plan.md`, and `tasks.md`.