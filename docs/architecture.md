# Banking Services Architecture

## System Architecture Diagram

```mermaid
flowchart TB
    subgraph Client["Client Layer"]
        WEB[Web Browser]
        MOBILE[Mobile App]
        API_CLIENT[API Client]
    end

    subgraph Gateway["API Gateway Layer"]
        GW[API Gateway<br/>:8085]
    end

    subgraph Security["Security Layer"]
        KC[Keycloak<br/>OAuth2/OIDC<br/>:4000]
    end

    subgraph Discovery["Service Discovery"]
        EUR[Eureka Server<br/>:8761]
        CFG[Config Server<br/>:8888]
    end

    subgraph Services["Business Services"]
        ACC[Account Service<br/>:8080]
        CARD[Card Service<br/>:9000]
        LOAN[Loan Service<br/>:8090]
        NOTIF[Notification Service<br/>:9010]
    end

    subgraph Messaging["Message Broker"]
        RMQ[RabbitMQ<br/>:5672]
    end

    subgraph Databases["Data Layer"]
        PG_ACC[(PostgreSQL<br/>Accounts<br/>:5432)]
        PG_CARD[(PostgreSQL<br/>Cards<br/>:5433)]
        PG_LOAN[(PostgreSQL<br/>Loans<br/>:5434)]
    end

    subgraph Cache["Caching Layer"]
        REDIS[(Redis<br/>:6379)]
    end

    subgraph Monitoring["Observability"]
        ZIP[Zipkin<br/>:9411]
        PGA[pgAdmin<br/>:5050]
    end

    %% Client connections
    WEB --> GW
    MOBILE --> GW
    API_CLIENT --> GW

    %% Gateway connections
    GW --> KC
    GW --> ACC
    GW --> CARD
    GW --> LOAN
    GW --> REDIS

    %% Service Discovery
    ACC --> EUR
    CARD --> EUR
    LOAN --> EUR
    NOTIF --> EUR
    GW --> EUR

    %% Config Server
    ACC --> CFG
    CARD --> CFG
    LOAN --> CFG
    NOTIF --> CFG
    GW --> CFG
    EUR --> CFG

    %% Database connections
    ACC --> PG_ACC
    CARD --> PG_CARD
    LOAN --> PG_LOAN

    %% Inter-service communication
    ACC -.->|OpenFeign| CARD
    ACC -.->|OpenFeign| LOAN

    %% Async messaging
    ACC -->|Events| RMQ
    RMQ -->|Consume| NOTIF

    %% Tracing
    ACC --> ZIP
    CARD --> ZIP
    LOAN --> ZIP
    NOTIF --> ZIP
    GW --> ZIP

    %% Database admin
    PGA --> PG_ACC
    PGA --> PG_CARD
    PGA --> PG_LOAN
```

## Service Communication Flow

```mermaid
sequenceDiagram
    participant C as Client
    participant GW as API Gateway
    participant KC as Keycloak
    participant ACC as Account Service
    participant CARD as Card Service
    participant LOAN as Loan Service
    participant RMQ as RabbitMQ
    participant NOTIF as Notification Service

    C->>GW: POST /api/accounts (Create Account)
    GW->>KC: Validate JWT Token
    KC-->>GW: Token Valid
    GW->>ACC: Forward Request
    ACC->>ACC: Create Account
    ACC->>CARD: GET /cards (OpenFeign)
    CARD-->>ACC: Card Details
    ACC->>LOAN: GET /loans (OpenFeign)
    LOAN-->>ACC: Loan Details
    ACC->>RMQ: Publish Welcome Event
    RMQ->>NOTIF: Consume Event
    NOTIF->>NOTIF: Send Email/SMS
    ACC-->>GW: Account Created Response
    GW-->>C: 201 Created
```

## Service Registration Flow

```mermaid
sequenceDiagram
    participant SVC as Microservice
    participant CFG as Config Server
    participant EUR as Eureka Server

    SVC->>CFG: Fetch Configuration
    CFG-->>SVC: Return Config (YAML)
    SVC->>SVC: Initialize with Config
    SVC->>EUR: Register Instance
    EUR-->>SVC: Registration Confirmed
    loop Heartbeat
        SVC->>EUR: Send Heartbeat (30s)
        EUR-->>SVC: Acknowledge
    end
```

## Circuit Breaker Pattern

```mermaid
stateDiagram-v2
    [*] --> Closed
    Closed --> Open: Failure Threshold Exceeded
    Open --> HalfOpen: Wait Duration Elapsed
    HalfOpen --> Closed: Success
    HalfOpen --> Open: Failure

    Closed: Normal Operation
    Closed: Requests pass through

    Open: Circuit Tripped
    Open: Requests fail fast
    Open: Fallback response

    HalfOpen: Testing Recovery
    HalfOpen: Limited requests allowed
```

## Database Schema Overview

```mermaid
erDiagram
    CUSTOMER ||--o{ ACCOUNT : has
    ACCOUNT ||--o{ CARD : linked_to
    ACCOUNT ||--o{ LOAN : linked_to

    CUSTOMER {
        bigint id PK
        string name
        string email
        string mobile_number
        timestamp created_at
    }

    ACCOUNT {
        bigint account_number PK
        bigint customer_id FK
        string account_type
        string branch_address
        timestamp created_at
    }

    CARD {
        bigint card_id PK
        string mobile_number
        string card_number
        string card_type
        int total_limit
        int amount_used
        timestamp created_at
    }

    LOAN {
        bigint loan_id PK
        string mobile_number
        string loan_number
        string loan_type
        int total_loan
        int amount_paid
        timestamp created_at
    }
```

## Event-Driven Architecture

```mermaid
flowchart LR
    subgraph Producers
        ACC[Account Service]
    end

    subgraph RabbitMQ["RabbitMQ Exchanges"]
        EX1[send-notifications]
        EX2[welcome-notifications]
        EX3[transactions-notifications]
    end

    subgraph Queues
        Q1[welcome-notifications.notification-service]
        Q2[transactions-notifications.notification-service]
    end

    subgraph Consumers
        NOTIF[Notification Service]
    end

    ACC -->|Account Created| EX1
    ACC -->|Welcome Event| EX2
    ACC -->|Transaction Event| EX3

    EX2 --> Q1
    EX3 --> Q2

    Q1 --> NOTIF
    Q2 --> NOTIF

    NOTIF -->|Email| EMAIL[SMTP Server]
    NOTIF -->|SMS| SMS[SMS Gateway]
```

## Deployment Architecture

```mermaid
flowchart TB
    subgraph Docker["Docker Environment"]
        subgraph Infrastructure
            PG1[postgres-accounts<br/>:5432]
            PG2[postgres-cards<br/>:5433]
            PG3[postgres-loans<br/>:5434]
            RMQ[rabbitmq<br/>:5672/:15672]
            REDIS[redis<br/>:6379]
            KC[keycloak<br/>:4000]
            ZIP[zipkin<br/>:9411]
            PGA[pgadmin<br/>:5050]
        end
    end

    subgraph JVM["JVM Processes"]
        CFG[Config Server<br/>:8888]
        EUR[Eureka Server<br/>:8761]
        GW[API Gateway<br/>:8085]
        ACC[Account Service<br/>:8080]
        CARD[Card Service<br/>:9000]
        LOAN[Loan Service<br/>:8090]
        NOTIF[Notification Service<br/>:9010]
    end

    subgraph Network["bank-ms-net"]
        Infrastructure
    end

    JVM --> Network
```

## Technology Stack

```mermaid
mindmap
    root((Banking<br/>Services))
        Core Framework
            Spring Boot 4.0
            Spring Cloud 2025.1
            Java 25
        Service Discovery
            Netflix Eureka
            Spring Cloud Config
        API Gateway
            Spring Cloud Gateway
            Spring Security OAuth2
        Resilience
            Resilience4j
            Circuit Breaker
            Rate Limiter
        Messaging
            RabbitMQ
            Spring Cloud Stream
        Data
            PostgreSQL
            Spring Data JPA
            Redis Cache
        Security
            Keycloak
            JWT/OAuth2
            RBAC
        Observability
            Micrometer
            Zipkin
            Spring Actuator
```
