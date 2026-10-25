# 001 · Journey Fare Comparison — Plan

## Approach

Add a small backend API that accepts a journey request and returns clearly labeled illustrative ticket offers. Keep the scope independent of timetables, station persistence, external providers, and frontend work so the request and response can be tried directly in Swagger UI.

Treat the demo offer source as replaceable at the infrastructure boundary. Keep validation and comparison rules in the domain, and expose the use case through the existing `interfaces` REST layer. Do not introduce an application package or extra abstractions unless implementation demonstrates they are needed and remains compliant with the repository's architecture rules.

## API Contract

- **Operation:** `POST /api/v1/journey-fare-comparisons`
- **Request:** origin, destination, and travel date (ISO-8601 calendar date).
- **Passenger assumption:** one adult; no passenger field in this iteration.
- **Response:** requested journey and a list of offers ordered by total price. Each offer includes a demo provider label, total price, ISO currency code, and an explicit demo indicator.
- **No-results behavior:** a valid request with no matching demo offers returns an empty list.
- **Errors:** invalid fields return the project's standard RFC 7807 Problem Details response.

The endpoint and field names above are the implemented contract.

## Implementation Steps

1. Define domain value types and validation for a journey request and illustrative fare offer; keep framework imports out of `domain`.
2. Implement the comparison use case and a deterministic demo-offer source, with no persistence or network calls.
3. Add the REST endpoint and request/response DTOs under `interfaces`, following the existing controller and DTO conventions.
4. Document request and response examples for Swagger UI.
5. Add focused unit and API tests for validation, demo labeling, empty results, and ascending price ordering.
6. Run the relevant Maven tests and ensure the existing architecture checks still pass.

## Decisions

- **Demo offers first:** makes the API usable in Swagger before external fare sources are selected; clearly fictional data avoids implying live availability.
- **Journey input without timetable selection:** origin, destination, and date define the comparison request; train times and station schedules are not available in this feature.
- **One adult:** keeps the first price comparison unambiguous without introducing passenger and discount rules.
- **No persistence:** avoids database schema changes for illustrative data.
- **Real providers later:** each provider integration requires a separately specified source, terms, failure behavior, and fare freshness rules.

## Risks

- Demo prices can be mistaken for real fares unless every response and Swagger example labels them clearly.
- Free-text station names can be ambiguous; station normalization and validation are deferred until a station catalog is prioritized.
- A future provider may return a journey or fare structure that differs from the initial demo contract; keep only the domain concepts justified by this feature.

## Implementation Summary

- Implemented `POST /api/v1/journey-fare-comparisons` with request validation and RFC 7807 Problem Details for invalid requests.
- Added a deterministic illustrative source with three clearly named demo operators, EUR prices, and explicit source/status fields.
- Kept journey validation and ascending-price ordering in the framework-free domain; wired the demo source through a domain port.
- Added Swagger request/response examples and tests for the API behavior, demo source, OpenAPI document, and architecture rules.
