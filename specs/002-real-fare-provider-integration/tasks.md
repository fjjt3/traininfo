# 002 · Real Fare Provider Integration — Tasks

## Discovery and Approval

- [ ] Research official operator/provider APIs and compare access, route coverage, terms, attribution, test environment, freshness, and rate limits.
- [ ] Select one provider and add its official API documentation and verified access requirements to `spec.md`.
- [ ] Confirm the provider supports the current journey request or document and approve required station/passenger input changes.
- [ ] Define external credential/configuration names and safe setup guidance without recording secret values.
- [ ] Finalize requirements, error behavior, response fields, acceptance criteria, and OpenAPI examples from verified provider behavior.
- [ ] Approve the completed specification before implementation.

## Implementation (after approval)

- [ ] Add typed provider HTTP client configuration, authentication, and bounded timeouts.
- [ ] Implement the infrastructure adapter for `TicketOfferSourcePort`.
- [ ] Map successful provider offers and freshness/source information into the domain contract.
- [ ] Surface no-offer results separately from provider errors, timeouts, rate limits, and authentication failures.
- [ ] Update API and Swagger documentation to describe real-provider coverage and fare status.
- [ ] Add fixture-based adapter and failure-path tests; add an approved sandbox check if available.
- [ ] Run unit, architecture, and integration verification and check secret handling.
- [ ] Verify acceptance criteria, mark implementation tasks complete, set the spec status to done, and update the roadmap.
