# Roadmap

_Order and status of features. Represents the view of "what is done, what is next, and what is coming". Each entry points to its respective folder under `features/`._

## Done ✅

_Completed features, in implementation order._

1. **000 · Project Setup & Dockerization** — Initial repository structure, Java 25 runtime setup, Docker Compose with PostgreSQL, and SDD foundation.

## Next 🔜

_Next feature to address. Ideally only one feature in progress at a time._

2. **001 · Station Catalog** — REST API to query and search train stations stored in PostgreSQL.

## Backlog / Ideas 💡

_Ideas aligning with the constitution, pending prioritization._

- **Origin-Destination Schedule Search** — Query available train journeys between two stations for a given date.
- **GTFS Static Ingestor** — Parser and importer for Adif GTFS static datasets into PostgreSQL.
- **Real-time Updates (GTFS-RT)** — Track and stream delays, platform changes, and service incidents.
- **Angular Web Frontend** — SPA client application for interactive timetable navigation.

> Every new feature must be created under `specs/NNN-feature-name/` with `spec.md`, `plan.md`, and `tasks.md` before writing code.