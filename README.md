# FlexFit

A fitness tracking and exercise management REST API built with Spring Boot 4.0.3 and Java 21.

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.0.3
- **Database:** PostgreSQL
- **ORM:** Spring Data JPA / Hibernate
- **Auth:** JWT (access + refresh tokens via httpOnly cookies)
- **Build:** Maven (wrapper included)
- **Testing:** JUnit 5, Spring Boot Test starters

## Prerequisites

- JDK 21
- PostgreSQL running on `localhost:5432`
- A database named `flexfit`
- `JWT_SECRET` environment variable set (see `.env`)

## Getting Started

### 1. Create the database

```sql
CREATE DATABASE flexfit;
```

### 2. Configure database credentials

Edit `src/main/resources/application.properties` to match your PostgreSQL username and password.

### 3. Build

```bash
./mvnw clean install
```

### 4. Run

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`.

## API Overview

All endpoints are prefixed with `/api/v1`.

| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| `GET` | `/api/v1/health` | No | Health check |
| `POST` | `/api/v1/auth/signup` | No | Register a new user |
| `POST` | `/api/v1/auth/login` | No | Login |
| `POST` | `/api/v1/auth/refresh` | No | Refresh access token |
| `POST` | `/api/v1/auth/logout` | No | Clear auth cookies |
| `GET` | `/api/v1/exercises` | JWT | List/filter exercises |
| `GET` | `/api/v1/exercises/{id}` | JWT | Get exercise details |
| `POST` | `/api/v1/profiles` | JWT | Create/update user profile |
| `GET` | `/api/v1/profiles` | JWT | Get user profile |

Additional Actuator endpoints at `/actuator/**`.

## Project Structure

```
src/main/java/com/harish/flexfit/
├── Application.java              # Entry point
├── config/                       # Security & CORS config
├── controller/                   # REST controllers
├── dto/                          # Request/response DTOs
├── entity/                       # JPA entities (User, UserProfile, Exercise, Equipment, WeightLog)
├── enums/                        # Enum types (muscle groups, difficulty, goals, etc.)
├── exception/                    # Custom exceptions & global handler
├── repo/                         # JPA repositories
├── security/                     # JWT utilities, auth filter, UserDetailsService
├── seeder/                       # Database seeders (equipment, exercises)
├── service/                      # Business logic interfaces & implementations
├── util/                         # Unit conversion utilities
└── validator/                    # Custom validation annotations
```

## Testing

```bash
./mvnw test
```

## Environment Variables

| Variable | Description |
|----------|-------------|
| `JWT_SECRET` | Base64-encoded HMAC-SHA256 secret key for JWT signing |

## License

Unlicensed — private project.
