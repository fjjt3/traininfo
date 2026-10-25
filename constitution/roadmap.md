# Roadmap

_Order and status of features. Represents the view of "what is done, what is next, and what is coming". Each feature points to its folder under `specs/`._

## Done ✅

_Completed features, in implementation order._

1. **000 · Project Setup & Dockerization** — Initial repository structure, Java 25 runtime setup, Docker Compose with PostgreSQL, and SDD foundation.
2. **001 · Journey Fare Comparison** — Backend endpoint to compare illustrative offers by origin, destination, and travel date; documented and testable in Swagger.

## Next 🔜

_Next feature to address. Ideally only one feature in progress at a time._

3. **002 · Real Fare Provider Integration** — Integrate one officially supported provider after verifying its API, access terms, route coverage, and required journey inputs. The specification is proposed and not ready for implementation until the provider-selection gate is complete.

## Backlog / Ideas 💡

_Ideas aligning with the constitution, pending prioritization._

- **Origin-Destination Schedule Search** — Query available train journeys between two stations for a given date.
- **Station Catalog** — REST API to query and search train stations stored in PostgreSQL.
- **GTFS Static Ingestor** — Parser and importer for Adif GTFS static datasets into PostgreSQL.
- **Real-time Updates (GTFS-RT)** — Track and stream delays, platform changes, and service incidents.
- **Angular Web Frontend** — SPA client for interactive timetable navigation and, when prioritized, the journey and fare comparison experience.

> Every new feature must be created under `specs/NNN-feature-name/` with `spec.md`, `plan.md`, and `tasks.md` before writing code.