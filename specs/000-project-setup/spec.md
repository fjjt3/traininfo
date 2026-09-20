# 000 · Project Setup & Dockerization

**Status:** done

## What It Does

Establishes the foundational Java 25 backend architecture with PostgreSQL running via Docker Compose, configuring Spring Boot 3.x, database connectivity, and base package structure across `domain`, `infrastructure`, and `interfaces`.

## Why

Provides a clean, reproducible execution environment from day one, ensuring seamless local development with PostgreSQL and verifying package dependency rules before business features are developed.

## Acceptance Criteria

- [x] Java 25 Spring Boot application starts successfully.
- [x] PostgreSQL database instance runs in a Docker container via `docker-compose.yml`.
- [x] Database connection is established and verified via Spring Boot health check or database connection test.
- [x] Base package layout (`domain`, `infrastructure`, `interfaces`) is established with clear layer separation.
- [x] Application responds to a basic `/actuator/health` or baseline health REST endpoint.

## Out of Scope

- [ ] Domain entity modeling and specific train schedule business logic (covered in upcoming feature specs).
- [ ] Production deployment pipelines and Kubernetes manifests.