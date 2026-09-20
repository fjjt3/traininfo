# AGENTS.md

## Toolchain (Windows + Git Bash)

- No `mvn` on PATH — use the wrapper `./mvnw` (`mvnw.cmd` works in cmd/PowerShell).
- SDKMAN is installed at `C:\sdkman`, not `~/.sdkman`. Java 25 is `25.0.4-tem`. Every build shell needs:
  `export JAVA_HOME="/c/sdkman/candidates/java/25.0.4-tem"` (source `/c/sdkman/bin/sdkman-init.sh` first if you want `sdk`/`java` on PATH).
- Docker Desktop must be running for `docker compose` and integration tests; the engine takes ~1 min to become ready after launch.

## Commands

- `docker compose up -d` — PostgreSQL 16 (`traininfo-db`, port 5432, container healthcheck). Required before running the app.
- `./mvnw test` — unit + ArchUnit tests.
- `./mvnw verify` — also runs Testcontainers ITs via failsafe (`src/test/java/**/*IT.java`); needs Docker.
- Single IT: `./mvnw -Dit.test=ProjectSetupIT verify`.
- `./mvnw spring-boot:run` — app on port 8080. Verify with `GET /api/v1/health` (returns `{"status":"UP","database":"UP"}`) or `/actuator/health`.
- API docs (springdoc, added to `pom.xml`): Swagger UI at `http://localhost:8080/swagger-ui/index.html`, OpenAPI spec at `GET /v3/api-docs`.
- Config is `src/main/resources/application.yml` only — do not add `.properties`.
- DB schema is owned by Flyway (`src/main/resources/db/migration/`, `ddl-auto: validate`). Add a numbered migration for schema changes, never hand-edit the DB.

## Architecture (enforced by `ArchitectureTest`)

- `com.traininfo.domain` — pure business logic and port interfaces. Zero framework imports (Spring/Jakarta-persistence/Jackson) is a hard constitution rule. Never add any.
- `com.traininfo.infrastructure` — DB adapters, JPA entities, repositories, external adapters.
- `com.traininfo.interfaces` — REST controllers and request/response DTOs (use records). Controllers belong here, NOT in `infrastructure` (a line in `constitution/tech-stack.md` saying otherwise is stale).
- There is no `com.traininfo.application` package yet.
- Never return or pass `null`; use `Optional`, records, or empty collections.

## SDD workflow

- Features are spec'd under `specs/NNN-feature-name/{spec.md,plan.md,tasks.md}` — the real path is `specs/`; `features/...` references in spec headers are stale. No production code before approved SDD docs.
- After implementing: tick `tasks.md` boxes and `spec.md` acceptance criteria, flip `spec.md` status to done, and update `constitution/roadmap.md`.
- `constitution/tech-stack.md` holds the binding Hard Rules; `constitution/roadmap.md` tracks what's next (currently 001 · Station Catalog).

## Code style

- When writing or reviewing code, apply the repo-local clean-code skill: `.opencode/skills/backend-dev/SKILL.md` (Uncle Bob, all languages).