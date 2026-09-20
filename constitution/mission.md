# Mission

_Defines the purpose of the project. It serves as the primary reference to decide whether a feature fits into the scope or not._

## What We Are Building

`traininfo` is a backend platform designed to query, aggregate, and serve train schedules and route information, providing fast, reliable, and clean APIs for rail services.

1. **Schedule & Query Service** — Exposes clean REST endpoints to query train schedules by station, origin, destination, and date.
2. **Transport Data Ingestor (GTFS/Adif)** — Module responsible for parsing and integrating official transport data sources (Adif static files, GTFS feeds).
3. **Rail Domain Core** — Isolated business logic to compute train routes, connections, and service status.

## Target Audience

- **Passengers/Travelers:** Looking for quick and clear train schedule lookups between stations.
- **Developers & Frontend Applications (Angular):** Consuming a predictable, well-documented REST API decoupled from external data providers.
- **Maintainers & Contributors:** Requiring a clean, well-structured codebase following Clean Code and SDD to easily scale the system.

## Principles

- **Clean & Hexagonal Architecture** — Core domain logic does not depend on frameworks, ORMs, databases, or external providers (Adif/GTFS).
- **Specification-Driven Development (SDD)** — No production code is written before a feature has its approved `spec.md`, `plan.md`, and `tasks.md` files in place.
- **Simplicity & Readability (Clean Code)** — Small methods, intention-revealing names, immutability, and zero unnecessary side effects.
- **Modern Java 25 Capabilities** — Leverage Records, Pattern Matching, Sealed Interfaces, and Virtual Threads for clean concurrency and concise code.
- **Container-First Development** — PostgreSQL and application runtimes are fully containerized using Docker Compose from day one for seamless local setup.

## What It Is NOT

- **NOT a Ticket Booking/Payment System:** Does not handle seat reservations, payments, or ticketing transactions.
- **NOT Tightly Coupled to a Single Provider:** Does not rely directly on Adif internal models; uses its own unified domain representation.