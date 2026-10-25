# 002 · Real Fare Provider Integration — Plan

**Status:** proposed — provider selection required before approval

## Approach

Integrate one real provider behind the existing `TicketOfferSourcePort`. Keep network protocols, authentication, provider DTOs, and error translation in `infrastructure`. Map successful responses into the framework-free domain offer model and preserve the comparison endpoint from feature 001 where the provider can support its request contract.

Do not begin provider implementation until the selection gate in `spec.md` is completed and the spec is approved. If provider research shows that free-text stations, the one-adult assumption, or the current fare model cannot support a compliant integration, update and approve the requirements before production code is written.

## Discovery and Approval Gate

1. Compare candidate official operator/provider APIs against route coverage, permitted use, access approval, test environment, required station identifiers, rate limits, and fare freshness.
2. Select one feasible provider and cite its official documentation in `spec.md`.
3. Confirm access and credentials without committing secrets; record local/deployment configuration names and safe setup instructions.
4. Document provider restrictions and decide whether a station-code mapping or a change to the journey request is required.
5. Update acceptance criteria, response freshness fields, API examples, risks, and tasks to match verified provider behavior.
6. Obtain approval of the completed specification before implementation.

## Implementation Steps (after approval)

1. Define provider configuration and typed request/response models in the infrastructure layer.
2. Implement an HTTP client using the project's supported Spring mechanisms, with explicit connection/read timeouts and provider authentication.
3. Implement an adapter for `TicketOfferSourcePort` that maps provider offers to domain objects and preserves source, currency, amount, and freshness.
4. Translate provider no-result responses separately from rate limits, authentication errors, malformed responses, and outages.
5. Decide and implement the approved behavior for provider failures at the API boundary; do not return success-shaped empty/demo responses for failures.
6. Update OpenAPI descriptions and response schemas to accurately explain provider coverage and fare freshness/status.
7. Add unit tests with mocked HTTP responses and integration/contract tests using fixtures; use a sandbox only when access is available and permitted.
8. Run unit, architecture, and integration tests and verify no credentials appear in logs, responses, or tracked files.

## Decisions

- **One provider first:** limits scope and proves the adapter seam before adding provider-specific complexity.
- **Official API only:** avoids brittle scraping and respects provider terms and access controls.
- **Reuse the existing port:** keeps provider protocol details out of domain and preserves the comparison use case where contracts match.
- **Provider is TBD:** no company or API capability is asserted before verification.
- **No MCP in the runtime integration:** the Spring Boot application calls a provider API through its adapter; MCP is a separate tool-integration protocol and is not required here.
- **No automatic demo fallback:** a provider outage must not be represented as a successful comparison or a real price.

## Risks

- Some operators provide no public fare API, require commercial approval, or restrict redistribution of fare data.
- An API may require station identifiers, passenger categories, or journey data not present in feature 001.
- Provider fare data may be indicative or expire quickly; freshness and availability must follow the provider's actual semantics.
- Credentials, quotas, and sandbox access can block development or automated checks.
- Provider outages and schema changes require explicit diagnostics and adapter maintenance; the integration cannot guarantee continued operation without updates.
