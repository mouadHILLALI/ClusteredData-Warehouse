# WarehouseData

WarehouseData is a **Spring Boot application** for managing deals and currencies, designed with **modular services**, **unit testing**, and **Dockerized deployment**.

---

## Features

- **Deal Management**: Create, validate, and store deals with proper currency checks.
- **Currency Validation**: Validates currency codes using a CSV-based reference list.
- **Unit Testing**: Fully tested services with Mockito and JUnit 5.
- **Code Coverage**: JaCoCo integration for measuring test coverage.
- **Dockerized Deployment**: Run the app and database in isolated containers.
- **CI/CD Ready**: Makefile with commands for building, testing, coverage, and Docker orchestration.

---

## Tech Stack

- **Backend**: Java 21, Spring Boot, Spring Data JPA
- **Database**: PostgreSQL
- **Testing**: JUnit 5, Mockito, JaCoCo
- **Build Tool**: Maven
- **Containerization**: Docker, Docker Compose

---

## Prerequisites

- Docker & Docker Compose installed
- Maven (if running tests locally outside Docker)
- Java 21

---

## Getting Started

### Clone the Repository

```bash
git clone https://github.com/mouadHILLALI/warehousedata.git
cd warehousedata
```

3. **Build and Run**
```bash
make up
```
This starts the application and PostgreSQL database using Docker Compose.

## Makefile Commands

- `make help`: Show available commands
- `make up`: Start Docker containers in detached mode
- `make down`: Stop and remove Docker containers
- `make all`: Start everything and build image

## API Endpoints

### Insert Deal
- **Endpoint**: `POST /api/v1/deals/import`
- **URL**: `http://localhost:8081/api/v1/deals`
- **Request JSON Example**:
```json
  {
  "id": "D0e7",
  "fromCurrency": "EUR",
  "toCurrency": "USD",
  "dealTimeStamp": "2025-11-11T17:00:00",
  "dealAmount": 50.00
}
```
- **Response** (HTTP 200):
```json
  {
  "id": "D0e7",
  "fromCurrency": "EUR",
  "toCurrency": "USD",
  "dealTimeStamp": "2025-11-11T17:00:00",
  "dealAmount": 50.00
}
```

### Insert Deals
- **Endpoint**: `POST /api/v1/deals/import/batch`
- **URL**: `http://localhost:8081/api/v1/deals`
- **Request JSON Example**:
```json
[
  {
    "id": "D001",
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "dealTimeStamp": "2025-11-11T10:30:00",
    "dealAmount": 1000.50
  },
  {
    "id": "D002",
    "fromCurrency": "GBP",
    "toCurrency": "USD",
    "dealTimeStamp": "2025-11-11T11:00:00",
    "dealAmount": 500.00
  },
  {
    "id": "D003",
    "fromCurrency": "JPY",
    "toCurrency": "USD",
    "dealTimeStamp": "2025-11-11T12:15:00",
    "dealAmount": 100000
  },
  {
    "id": "D004",
    "fromCurrency": "EUR",
    "toCurrency": "EUR",
    "dealTimeStamp": "2025-11-11T14:45:00",
    "dealAmount": 2000.75
  },
  {
    "id": "D005",
    "fromCurrency": "AUD",
    "toCurrency": "",
    "dealTimeStamp": "2025-11-11T15:30:00",
    "dealAmount": 750.25
  },
  {
    "id": "",
    "fromCurrency": "CAD",
    "toCurrency": "USD",
    "dealTimeStamp": "2025-11-11T16:00:00",
    "dealAmount": 1200.00
  },
  {
    "id": "D007",
    "fromCurrency": "XYZ",
    "toCurrency": "USD",
    "dealTimeStamp": "2025-11-11T17:00:00",
    "dealAmount": -50.00
  },
  {
    "id": "D008",
    "fromCurrency": "CHF",
    "toCurrency": "GBP",
    "dealTimeStamp": null,
    "dealAmount": 300.00
  }
]

```
- **Response** (HTTP 200):
```json
 [
  {
    "id": "D001",
    "fromCurrency": "USD",
    "toCurrency": "EUR",
    "dealTimeStamp": "2025-11-11T10:30:00",
    "dealAmount": 1000.50
  },
  {
    "id": "D002",
    "fromCurrency": "GBP",
    "toCurrency": "USD",
    "dealTimeStamp": "2025-11-11T11:00:00",
    "dealAmount": 500.00
  },
  {
    "id": "D003",
    "fromCurrency": "JPY",
    "toCurrency": "USD",
    "dealTimeStamp": "2025-11-11T12:15:00",
    "dealAmount": 100000
  }
]

```
## Request Validation

- **Fields Validated**:
    - `id`: Must not be blank
    - `fromCurrency`: Must be a valid 3-letter ISO code (e.g., "USD")
    - `toCurrency`: Must be a valid 3-letter ISO code (e.g., "SGD")
    - `dealAmount`: Must be positive and not null
- **Validation Mechanism**:
    - Uses Jakarta Validation annotations (`@NotBlank`, `@NotNull`, `@Positive`)
    - Custom currency validation against a CSV file of valid codes
    - Throws specific exceptions for invalid data (`InvalidCurrencyFormat`, `DuplicateRequestException`)

## Database Interaction

- **Database**: PostgreSQL
- **Entity**: `Deal` table with columns:
    - `id` (String, Primary Key)
    - `from_currency` (String, 3 chars)
    - `to_currency` (String, 3 chars)
    - `deal_timestamp` (LocalDateTime)
    - `deal_amount` (BigDecimal)
- **JPA**: Spring Data JPA with auditing for timestamp
- **Duplicate Prevention**: Unique constraint on `id` column

## Testing

- **Framework**: JUnit 5 with Spring Test
- **Coverage**:
    - Unit tests for service layer (`DealServiceImplTest`)
    - Currency validation tests (`CurrencyValidatorImplTest`)
- **Location**: `src/test/java`
- **Run**: `make test`

## Dockerization

- **Dockerfile**: Multi-stage build
    - Maven stage: Builds the application
    - Runtime stage: Runs the JAR with Eclipse Temurin JDK 21
- **Docker Compose**:
    - `app`: Spring Boot application on port 8081
    - `db`: PostgreSQL on port 5432
    - Volumes for persistent data and Maven cache

## Error Handling

- Custom exceptions for specific cases (duplicate deals, invalid currencies)
- Global exception handler returning structured `ErrorResponse`
- Logging with SLF4J for debugging and monitoring
