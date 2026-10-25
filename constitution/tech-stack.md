# Tech Stack & Conventions

_How the project is built and the rules that all code must follow. This technical reference must not be violated by any feature plan._

## Technologies

- **Language:** Java 25 (utilizing Records, Pattern Matching, Sealed Classes, and Virtual Threads).
- **Framework / Runtime:** Spring Boot 3.x with Java 25 compatibility.
- **Web Client:** Angular SPA consuming the documented REST API; frontend tooling is defined when that feature is prioritized.
- **Database:** PostgreSQL 16+ (running in Docker container).
- **Migration Tool:** Flyway for version-controlled database schema migrations.
- **Containerization:** Docker & Docker Compose.
- **Testing:** JUnit 5, AssertJ, Mockito, Testcontainers (PostgreSQL), and ArchUnit.

## Key Files & Modules

- `docker-compose.yml` — Local development infrastructure (PostgreSQL container, environment configuration).
- `src/main/java/com/traininfo/domain/` — Pure domain model, value objects, and port interfaces (zero framework dependencies).
- `src/main/java/com/traininfo/application/` — Use cases and service orchestration, if introduced by an approved feature.
- `src/main/java/com/traininfo/infrastructure/` — Database persistence adapters, JPA entities, repositories, and external provider adapters.
- `src/main/java/com/traininfo/interfaces/` — REST controllers and request/response DTOs.
- `src/main/resources/db/migration/` — Flyway SQL migration scripts.
- `specs/` — SDD specification, planning, and task files for each feature.

## Commands

- `docker compose up -d` — Starts local PostgreSQL database container.
- `./mvnw spring-boot:run` — Starts the Spring Boot application locally.
- `./mvnw test` — Runs unit and architecture tests.
- `./mvnw verify` — Runs the verification suite, including integration tests when Docker is available.

## Data Model / Domain

- `Station` — Represents a train station (`id`, `adifCode`, `name`, `latitude`, `longitude`).
- `TrainService` — Details of a train service (`trainNumber`, `serviceType` [e.g., AVE, Cercanias, MD], `operator`).
- `Schedule` — Station arrival and departure timing (`arrivalTime`, `departureTime`, `platform`).
- `Route` — Ordered collection of station stops for a specific train service.
- `TicketOffer` — A ticket option from a supported operator or provider, with its fare source and retrieval time.
- `JourneyTotal` — The calculated total for a selected journey based on available ticket offers; it must not imply a confirmed fare when the source does not provide one.

## Conventions

- **Naming:** `camelCase` for variables/methods, `PascalCase` for classes/interfaces/records, `SNAKE_CASE` for constants.
- **Modern Java Practices:** Prefer `records` for immutable DTOs and value objects; use `Optional` for nullable values.
- **Test Placement:** Mirrored under `src/test/java/`, suffixed with `Test` (unit) or `IT` (integration).
- **Error Handling:** Domain-specific exceptions mapped to RFC 7807 Problem Details HTTP responses at the API boundary.

## Hard Rules

- **Zero Framework Imports in Domain:** Classes in `domain` must NEVER import `org.springframework.*`, `jakarta.persistence.*`, `com.fasterxml.jackson.*`, or any external library.
- **No `null` Passing or Returning:** Explicitly forbidden. Use `Optional` or empty collections.
- **SDD Enforcement:** No pull requests or production code without an approved `specs/NNN-feature-name/` directory containing `spec.md`, `plan.md`, and `tasks.md`.
- **Fare Transparency:** Comparisons and calculated totals must distinguish unavailable or indicative prices from confirmed offers and identify the supporting source. Booking, reservation, and payment flows require a separately approved change to project scope.