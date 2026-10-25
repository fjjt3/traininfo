# Mission

_Defines the purpose of the project. It serves as the primary reference to decide whether a feature fits into the scope or not._

## What We Are Building

`traininfo` is a rail journey information platform with a backend API and a web client. It helps travelers find journeys, compare ticket offers from different train operators, and calculate the total price for a selected journey.

1. **Journey Search & Comparison** — Finds journeys between stations and presents comparable ticket offers from supported operators, including the calculated total for the selected journey.
2. **Transport Data Ingestor** — Parses and integrates supported transport data sources, such as Adif datasets and GTFS feeds.
3. **Rail Domain Core** — Isolated business logic to compute train routes, connections, service status, and journey totals from available ticket offers.
4. **Web Client** — Lets travelers search for a journey, compare available offers, and select an option to see its calculated total.

## Target Audience

- **Passengers/Travelers:** Looking for clear journey options and ticket comparisons across supported train operators.
- **Developers & Frontend Applications (Angular):** Consuming a predictable, well-documented REST API decoupled from external data providers.
- **Frontend Users:** Comparing available journey and ticket options through an accessible web interface.
- **Maintainers & Contributors:** Requiring a clean, well-structured codebase following Clean Code and SDD to easily scale the system.

## Principles

- **Clean & Hexagonal Architecture** — Core domain logic does not depend on frameworks, ORMs, databases, or external providers (Adif/GTFS).
- **Specification-Driven Development (SDD)** — No production code is written before a feature has its approved `spec.md`, `plan.md`, and `tasks.md` files in place.
- **Simplicity & Readability (Clean Code)** — Small methods, intention-revealing names, immutability, and zero unnecessary side effects.
- **Modern Java 25 Capabilities** — Leverage Records, Pattern Matching, Sealed Interfaces, and Virtual Threads for clean concurrency and concise code.
- **Container-First Development** — PostgreSQL and application runtimes are fully containerized using Docker Compose from day one for seamless local setup.

## What It Is NOT

- **NOT a Ticket Booking/Payment System:** Compares available ticket offers and calculates journey totals, but does not reserve seats, sell tickets, process payments, or complete ticketing transactions.
- **NOT Tightly Coupled to a Single Provider:** Does not rely directly on Adif internal models; uses its own unified domain representation.

Ticket offers must come from supported data sources. The platform must not present calculated or unavailable prices as confirmed live fares.