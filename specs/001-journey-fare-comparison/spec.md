# 001 · Journey Fare Comparison

**Status:** done

## What It Does

Provides a backend API to compare illustrative ticket offers for a journey between a user-supplied origin and destination on a selected date. The endpoint can be exercised through Swagger UI without requiring a station catalog, timetable data, or an external fare provider.

The first iteration uses clearly identified demo offers. It does not claim to return live or bookable fares.

## Why

Establishes the backend journey and fare-comparison flow before integrating real operator data or building a web client. It lets contributors verify the API contract and comparison behavior interactively in Swagger.

## Users and Stories

- As a traveler, I can submit an origin, destination, and travel date to see comparable ticket offers.
- As a traveler, I can distinguish illustrative demo prices from confirmed fares.
- As a developer, I can try valid and invalid comparison requests in Swagger UI.

## Functional Requirements

- **FR-001:** The API shall accept a journey origin, destination, and travel date.
- **FR-002:** The API shall reject requests with missing or invalid journey fields, including an origin equal to the destination.
- **FR-003:** The API shall return zero or more ticket offers for a valid request, ordered from lowest to highest total price.
- **FR-004:** Each returned offer shall identify its demo source, currency, total price, and demo status.
- **FR-005:** Demo prices and providers shall be clearly labeled as illustrative and shall not be presented as live, confirmed, or bookable fares.
- **FR-006:** The API shall expose its request and response schemas and example requests in Swagger UI.
- **FR-007:** For this iteration, the comparison shall assume one adult traveler. Passenger selection is not included.

## Acceptance Criteria

- [x] A valid request containing origin, destination, and travel date can be submitted from Swagger UI.
- [x] The response contains comparable demo offers in ascending order by total price, or an empty offer list when no demo offers are available.
- [x] Every returned offer identifies its demo status and source and uses an explicit currency.
- [x] No response represents a demo price as a live, confirmed, or bookable fare.
- [x] Invalid requests, including missing fields and identical origin and destination, produce documented client errors.
- [x] The comparison does not require station records, train schedules, timetable lookup, or a real operator integration.
- [x] Automated tests cover successful comparison, price ordering, and invalid-request validation.

## Out of Scope

- Real-time or confirmed fares from train operators or third-party providers.
- Station catalog, timetable data, train selection, or schedule search.
- Seat availability, reservations, ticket purchase, or payment.
- Passenger count, passenger categories, discounts, and fare rules.
- Web frontend implementation.
- Persistence of demo offers or user searches.

## Assumptions and Constraints

- Origin and destination are submitted as text; this feature does not validate them against a station catalog.
- The first iteration uses explicitly illustrative demo offers to validate the API flow, not to inform real travel purchases.
- The implemented API is `POST /api/v1/journey-fare-comparisons`; the request fields are `origin`, `destination`, and `travelDate`.
- The repository's architecture, error-handling, no-null, and SDD rules remain binding.
