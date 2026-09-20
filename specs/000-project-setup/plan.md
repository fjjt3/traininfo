# 000 · Project Setup & Dockerization — Plan

## Approach

Configure the initial Spring Boot application using Java 25 and establish a containerized PostgreSQL development environment. The package layout follows Hexagonal/Clean architecture principles across three distinct layers: `domain` (pure business logic/services), `infrastructure` (entities, persistence interfaces, adapters), and `interfaces` (REST controllers).

## Implementation

1. **Docker Infrastructure** — Create root `docker-compose.yml` to provision a PostgreSQL 16 container on port 5432.
2. **Configuration & Dependencies** — Configure `build.gradle` / `pom.xml` with Java 25 compatibility, Spring Data JPA, PostgreSQL driver, and Spring Actuator.
3. **Application Properties** — Define database connection parameters in `src/main/resources/application.yml` targeting local PostgreSQL.
4. **Layered Structure Setup** — Verify and align packages:
   - `com.traininfo.domain` — Business services and pure domain models.
   - `com.traininfo.infrastructure` — Spring Data JPA repositories, database entities, and persistence implementations.
   - `com.traininfo.interfaces` — REST controllers and DTO mappings.
5. **Health Endpoint Verification** — Expose a basic health check controller under `interfaces.rest` to validate application and database readiness.

## Decisions

- **PostgreSQL in Docker from Start** — Avoids H2 migration overhead later and keeps local dev identical to production.
- **Spring Boot Actuator for Health** — Standardized way to verify database and app health without custom boilerplate.

## Risks

- **Java 25 Framework Compatibility** — Requires verifying JVM flags and bytecode version compatibility with current Spring Boot versions.