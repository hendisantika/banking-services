# Banking Services

Banking Services using Microservice Architecture with Spring Cloud

## Architecture Overview

This project implements a microservices-based banking system using Spring Boot 4.0 and Spring Cloud 2025.1.0.

```
                    ┌─────────────────┐
                    │    Keycloak     │
                    │   (OAuth2/JWT)  │
                    │    Port: 4000   │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │   API Gateway   │
                    │   Port: 8085    │
                    └────────┬────────┘
                             │
         ┌───────────────────┼───────────────────┐
         │                   │                   │
         ▼                   ▼                   ▼
┌─────────────────┐ ┌─────────────────┐ ┌─────────────────┐
│ Account Service │ │  Card Service   │ │  Loan Service   │
│   Port: 8080    │ │   Port: 9000    │ │   Port: 8090    │
└────────┬────────┘ └────────┬────────┘ └────────┬────────┘
         │                   │                   │
         └───────────────────┼───────────────────┘
                             │
                    ┌────────▼────────┐
                    │   RabbitMQ      │
                    │   Port: 5672    │
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │  Notification   │
                    │    Service      │
                    │   Port: 9010    │
                    └─────────────────┘
```

## Services

| Service              | Port | Description                              |
|----------------------|------|------------------------------------------|
| Config Server        | 8888 | Centralized configuration management     |
| Eureka Server        | 8761 | Service discovery and registration       |
| API Gateway          | 8085 | Request routing, security, rate limiting |
| Account Service      | 8080 | Account and customer management          |
| Card Service         | 9000 | Card management                          |
| Loan Service         | 8090 | Loan management                          |
| Notification Service | 9010 | Email/SMS notifications (event-driven)   |

## Infrastructure Services (Docker)

| Service               | Port        | Description                   |
|-----------------------|-------------|-------------------------------|
| PostgreSQL (Accounts) | 5432        | Account service database      |
| PostgreSQL (Cards)    | 5433        | Card service database         |
| PostgreSQL (Loans)    | 5434        | Loan service database         |
| RabbitMQ              | 5672, 15672 | Message broker                |
| Keycloak              | 4000        | OAuth2/OIDC identity provider |
| Redis                 | 6379        | Caching                       |
| Zipkin                | 9411        | Distributed tracing           |
| pgAdmin               | 5050        | PostgreSQL admin UI           |

## Technology Stack

- **Java 25**
- **Spring Boot 4.0.0**
- **Spring Cloud 2025.1.0**
- **Spring Cloud Gateway** - API Gateway
- **Netflix Eureka** - Service Discovery
- **Spring Cloud Config** - Centralized Configuration
- **Spring Cloud OpenFeign** - Declarative HTTP Client
- **Resilience4j** - Circuit Breaker, Rate Limiter
- **Spring Cloud Stream** - Event-driven messaging
- **RabbitMQ** - Message Broker
- **PostgreSQL** - Database
- **Keycloak** - Identity and Access Management
- **Micrometer + Zipkin** - Distributed Tracing

## Prerequisites

- Java 25
- Maven 3.9+
- Docker & Docker Compose

## Getting Started

### 1. Start Infrastructure Services

```bash
cd docker-composes/default
docker-compose up -d
```

### 2. Build All Services

```bash
# Build each service
cd services/config-server && ./mvnw clean package -DskipTests
cd services/eureka-server && ./mvnw clean package -DskipTests
cd services/api-gateway && ./mvnw clean package -DskipTests
cd services/account-service && ./mvnw clean package -DskipTests
cd services/card-service && ./mvnw clean package -DskipTests
cd services/loan-service && ./mvnw clean package -DskipTests
cd services/notification-service && ./mvnw clean package -DskipTests
```

### 3. Start Services (in order)

```bash
# 1. Config Server (must start first)
java -jar services/config-server/target/config-server-0.0.1-SNAPSHOT.jar

# 2. Eureka Server
java -jar services/eureka-server/target/eureka-server-0.0.1-SNAPSHOT.jar

# 3. Other services (can start in parallel)
java -jar services/api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
java -jar services/account-service/target/account-service-0.0.1-SNAPSHOT.jar
java -jar services/card-service/target/card-service-0.0.1-SNAPSHOT.jar
java -jar services/loan-service/target/loan-service-0.0.1-SNAPSHOT.jar
java -jar services/notification-service/target/notification-service-0.0.1-SNAPSHOT.jar
```

## Service Endpoints

### Eureka Dashboard

- URL: http://localhost:8761

### API Gateway Routes

All API requests go through the gateway at `http://localhost:8085`:

- `/api/accounts/**` - Account Service
- `/api/cards/**` - Card Service
- `/api/loans/**` - Loan Service

### Actuator Health Endpoints

- Config Server: http://localhost:8888/actuator/health
- Eureka Server: http://localhost:8761/actuator/health
- API Gateway: http://localhost:8085/actuator/health
- Account Service: http://localhost:8080/actuator/health
- Card Service: http://localhost:9000/actuator/health
- Loan Service: http://localhost:8090/actuator/health
- Notification Service: http://localhost:9010/actuator/health

## Configuration

Configuration files are centralized in the Config Server:

- Location: `services/config-server/src/main/resources/config/`
- Files: `accounts.yml`, `card-service.yml`, `loan-service.yml`, `api-gateway.yml`, `eureka-server.yml`

### Environment Profiles

- `default` - Development
- `dev` - Development with debug settings
- `qa` - QA environment

## Security

The API Gateway is secured with OAuth2/JWT using Keycloak:

- Keycloak Admin Console: http://localhost:4000
- Default credentials: admin / 1234

### Role-Based Access

- `/api/accounts/**` - Requires `ACCOUNTS` role
- `/api/cards/**` - Requires `CARDS` role
- `/api/loans/**` - Requires `LOANS` role

## Monitoring

### Distributed Tracing

- Zipkin UI: http://localhost:9411

### Database Management

- pgAdmin: http://localhost:5050
- Email: yuji@yopmail.com
- Password: 53cret

### Message Broker

- RabbitMQ Management: http://localhost:15672
- Username: admin
- Password: 1234

## Project Structure

```
banking-services/
├── docker-composes/
│   ├── default/          # Development environment
│   └── qa/               # QA environment
├── services/
│   ├── config-server/    # Centralized configuration
│   ├── eureka-server/    # Service discovery
│   ├── api-gateway/      # API routing & security
│   ├── account-service/  # Account domain
│   ├── card-service/     # Card domain
│   ├── loan-service/     # Loan domain
│   └── notification-service/  # Notifications
└── README.md
```

## Inter-Service Communication

### Synchronous (REST/OpenFeign)

- Account Service → Card Service (fetch customer cards)
- Account Service → Loan Service (fetch customer loans)
- Circuit breaker pattern with Resilience4j

### Asynchronous (RabbitMQ)

- Account Service → Notification Service (welcome emails)
- Exchanges: `send-notifications`, `welcome-notifications`, `transactions-notifications`

## License

This project is for educational purposes.
