# 002 · Real Fare Provider Integration

**Status:** proposed — provider selection required before approval

## What It Does

Replaces or supplements the illustrative offers from feature 001 with real ticket offers from one officially supported train operator or fare provider. The provider is intentionally not named yet: its official API, access terms, route coverage, and credential requirements must be verified before this specification is approved for implementation.

The integration adapts the provider's contract to TrainInfo's existing journey and ticket-offer model. Provider-specific models and protocol details remain outside the domain.

## Why

Feature 001 proves the journey comparison API with clearly labeled demo data. This feature establishes whether TrainInfo can obtain actual offers from a supported source and return them through the same comparison flow without claiming that unavailable or stale prices are current.

## Users and Stories

- As a traveler, I can receive real offers from the selected provider for a supported journey.
- As a traveler, I can identify the provider, currency, and freshness of each returned offer.
- As a traveler, I can distinguish a missing price or provider outage from a successful comparison with no offers.
- As a maintainer, I can adapt a provider response without coupling provider-specific types to the domain.

## Provider Selection Gate

Before approving implementation, record in this spec:

- The selected provider and a link to its official API documentation.
- Whether access is public, partner-only, or requires registration, approval, or credentials.
- Available test/sandbox access and required secrets/configuration; secrets must not be committed.
- Supported routes, countries, journey types, passenger assumptions, currencies, and offer fields.
- Provider rate limits, attribution or display conditions, and any restrictions on storing or redisplaying fares.
- Whether stations must be identified by provider-specific codes and, if so, the approved mapping source.

Do not implement an unofficial or undocumented scraping integration as a substitute for an unavailable API.

## Functional Requirements

- **FR-001:** The comparison flow shall request offers from one selected and documented real provider.
- **FR-002:** The adapter shall translate the provider response into TrainInfo's domain offer representation without importing provider DTOs into the domain.
- **FR-003:** Each offer shall identify the real provider, source, currency, total price, and provider retrieval time or equivalent freshness information.
- **FR-004:** Real offers shall not be marked as illustrative demo data. Existing demo data shall remain clearly identifiable and shall not be mixed with real offers without an explicit source/status distinction.
- **FR-005:** Valid provider responses with no matching offers shall produce a successful comparison with an empty offer list.
- **FR-006:** Provider timeouts, rate limiting, authentication failures, and unavailable service shall be surfaced as explicit API errors; they shall not be converted into empty offer lists or demo prices.
- **FR-007:** Provider credentials shall be supplied through external configuration or environment variables and shall never be returned by the API or written into source control.
- **FR-008:** Provider-specific limitations and fare freshness shall be described in the API documentation and response where applicable.
- **FR-009:** The integration shall comply with the provider's documented terms, attribution rules, rate limits, and data usage restrictions.

## Acceptance Criteria

- [ ] The provider selection gate is completed with official documentation and verified access requirements.
- [ ] A valid request for a supported journey can retrieve and map real provider offers.
- [ ] Every returned real offer has provider/source identity, a non-negative total price, currency, and retrieval/freshness information supported by the provider.
- [ ] The API distinguishes a successful no-offer response from provider errors, timeouts, rate limits, and authentication failures.
- [ ] Provider-specific request/response types remain in the infrastructure adapter; domain types have no provider or framework dependencies.
- [ ] Credentials are externally configured and absent from committed files and API responses.
- [ ] API/Swagger documentation explains provider coverage, fare freshness, and whether prices are indicative or confirmed according to the provider.
- [ ] Automated tests cover provider response mapping, empty results, malformed responses, and relevant failure cases without requiring a live provider call.
- [ ] An end-to-end or sandbox check verifies the integration if the selected provider makes an approved test environment available.

## Out of Scope

- Integrating multiple real providers in this feature.
- Scraping operator websites or bypassing access controls.
- Seat reservation, ticket purchase, payment, or booking redirects unless separately specified.
- A station catalog or timetable search, except for a minimal, documented station-code mapping proven necessary by the selected provider and separately approved.
- Persisting traveler searches or provider fares.
- An MCP server used as the application's provider integration layer.
- Frontend implementation.

## Assumptions and Constraints

- Feature 001 remains the public journey-comparison contract unless provider requirements demonstrate a necessary, approved API change.
- The initial journey request currently contains free-text origin, destination, and date and assumes one adult. Provider incompatibilities must be documented before implementation rather than silently approximated.
- Provider choice and API access are unresolved. This spec is not ready for implementation approval until the provider selection gate is completed.
- If no provider offers lawful, feasible access for the intended routes, pause implementation and revise the feature scope instead of shipping invented data as real fares.
- Project architecture, error handling, no-null rules, secret handling, and SDD workflow remain binding.
