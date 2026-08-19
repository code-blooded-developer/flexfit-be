# FlexFit

A smart, personalized fitness tracking and workout generation REST API built with **Spring Boot 4.0.3** and **Java 21**.

FlexFit automatically generates daily, tailored workout routines based on a user's fitness goals, workout split, experience level, duration budget, and equipment inventory, while providing real-time workout tracking and exercise logging.

---

## Features

- **Personalized Workout Engine**: Generates balanced daily workouts (cardio warmup, targeted warmup, compound/isolation main exercises, and cool-down stretches) based on workout splits (`FULL_BODY`, `UPPER_LOWER`, `PUSH_PULL_LEGS`, `BRO_SPLIT`), user experience, and goal-oriented rest/set/rep schemes.
- **7-Day Exercise Variation**: Prevents repetitive workouts by excluding exercises performed within the last 7 days.
- **Equipment-Aware Recommendations**: Recommends exercises compatible only with equipment the user owns or has access to.
- **Dynamic Workout Customization**: Swap exercises with smart alternatives (sorted by target muscle relevance), add/remove exercises on the fly, or replace movements.
- **Interactive Workout & Set Logging**: Log weights, reps, duration, distance, and notes in real time during workout execution.
- **Comprehensive Exercise & Equipment Library**: Hundreds of curated exercises with primary/secondary muscle groups, instructions, media links, and equipment requirements auto-seeded on startup.
- **Secure Authentication**: Stateless JWT authentication with access and refresh tokens managed via secure, HttpOnly cookies.
- **Biometric & Weight Tracking**: Profile management supporting imperial/metric units with automatic weight history logging.

---

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.0.3
- **Security:** Spring Security & JJWT (HttpOnly Cookies, Stateless)
- **Database:** PostgreSQL
- **ORM / Persistence:** Spring Data JPA / Hibernate
- **Build Tool:** Maven (Wrapper included)
- **Monitoring:** Spring Boot Actuator

---

## Prerequisites

- **JDK 21** or higher
- **PostgreSQL** running on `localhost:5432`
- Database created (e.g. `flexfit`)
- Environment variables configured (see [Environment Variables](#environment-variables))

---

## Getting Started

### 1. Create the Database

```sql
CREATE DATABASE flexfit;
```

### 2. Configure Environment Variables

Set the required environment variables in your environment or IDE run configuration (see table below).

### 3. Build the Application

```bash
./mvnw clean install
```

### 4. Run the Server

```bash
./mvnw spring-boot:run
```

The server starts at `http://localhost:8080`. Database tables and seeds (equipments & exercises) will initialize automatically on first startup.

---

## API Reference

All API routes are prefixed with `/api/v1`.

### Authentication (`/api/v1/auth`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/v1/auth/signup` | Public | Register a new user account & set auth cookies |
| `POST` | `/api/v1/auth/login` | Public | Authenticate user & issue JWT cookies |
| `POST` | `/api/v1/auth/refresh` | Public | Refresh access token using refresh cookie |
| `POST` | `/api/v1/auth/logout` | Public | Clear authentication cookies |

### User Profile (`/api/v1/profiles`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/v1/profiles` | JWT | Create or update user profile and log weight entry |
| `GET` | `/api/v1/profiles` | JWT | Fetch current user profile and metrics |

### Equipment Management (`/api/v1/equipments` & `/api/v1/user-equipments`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/v1/equipments` | JWT | List all available equipment and variants |
| `GET` | `/api/v1/equipments?environment={env}` | JWT | Get recommended equipment presets by environment (`HOME`, `COMMERCIAL_GYM`, etc.) |
| `GET` | `/api/v1/equipments/{id}` | JWT | Get equipment details by ID |
| `POST` | `/api/v1/equipments` | JWT | Create new equipment entry |
| `PUT` | `/api/v1/equipments/{id}` | JWT | Update existing equipment entry |
| `DELETE` | `/api/v1/equipments/{id}` | JWT | Delete equipment entry |
| `GET` | `/api/v1/user-equipments` | JWT | Get user's selected equipment inventory |
| `POST` | `/api/v1/user-equipments` | JWT | Save initial user equipment selections |
| `PUT` | `/api/v1/user-equipments` | JWT | Update user equipment selections |
| `DELETE` | `/api/v1/user-equipments` | JWT | Clear user equipment selections |

### Exercise Library (`/api/v1/exercises`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/v1/exercises` | JWT | Filter exercises by `muscle`, `difficulty`, `category`, and `equipmentIds` |
| `GET` | `/api/v1/exercises/{id}` | JWT | Get exercise details by ID |

### Workouts & Routine Management (`/api/v1/workouts`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/v1/workouts/today` | JWT | Get or auto-generate today's workout |
| `GET` | `/api/v1/workouts/history` | JWT | Get workout history for the past 7 days |
| `GET` | `/api/v1/workouts/{workoutId}` | JWT | Get specific workout details |
| `POST` | `/api/v1/workouts/{workoutId}/complete` | JWT | Mark a workout as completed |
| `POST` | `/api/v1/workouts/{workoutId}/skip` | JWT | Mark a workout as skipped |
| `DELETE` | `/api/v1/workouts/{workoutId}` | JWT | Delete a workout |
| `POST` | `/api/v1/workouts/{workoutId}/exercises` | JWT | Add exercises to workout |
| `DELETE` | `/api/v1/workouts/{workoutId}/exercises/{exerciseId}` | JWT | Remove an exercise from workout |
| `GET` | `/api/v1/workouts/{workoutId}/alternatives` | JWT | Get alternative exercises (prioritizing current workout target muscle) |
| `PUT` | `/api/v1/workouts/{workoutId}/exercises/{exerciseId}/replace/{altId}` | JWT | Replace an exercise with an alternative |
| `POST` | `/api/v1/workouts/{workoutId}/exercises/{exerciseId}/sets` | JWT | Log a set (weight, reps, distance, duration, notes) |
| `PUT` | `/api/v1/workouts/{workoutId}/exercises/{exerciseId}/sets/{setId}` | JWT | Update a logged set |
| `DELETE` | `/api/v1/workouts/{workoutId}/exercises/{exerciseId}/sets/{setId}` | JWT | Delete a logged set |

### System & Health

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `GET` | `/api/v1/health` | Public | Health check endpoint |
| `GET` | `/actuator/**` | Public | Spring Boot Actuator monitoring endpoints |

---

## Project Structure

```
src/main/java/com/harish/flexfit/
├── Application.java              # Spring Boot application entry point
├── config/                       # Security & CORS configuration
├── controller/                   # REST API controllers
├── dto/                          # Request and response data transfer objects
├── entity/                       # JPA database entities
├── enums/                        # Domain enums (MuscleGroup, FitnessGoal, WorkoutSplit, etc.)
├── exception/                    # Global exception handler & custom exceptions
├── repo/                         # Spring Data JPA repositories
├── security/                     # JWT utilities, auth filter, and UserDetailsService
├── seeder/                       # Database seeders (EquipmentSeeder, ExerciseSeeder)
├── service/                      # Business logic interfaces & implementations
├── util/                         # Unit conversion helpers (kg/lbs, cm/inches)
└── validator/                    # Custom validation annotations and validators
```

---

## Environment Variables

| Variable | Description | Example / Default |
|---|---|---|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC connection URL | `jdbc:postgresql://localhost:5432/flexfit` |
| `SPRING_DATASOURCE_USERNAME` | Database username | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | Database password | `postgres` |
| `SPRING_DATASOURCE_DRIVER_CLASS_NAME` | JDBC driver class name | `org.postgresql.Driver` |
| `SPRING_JPA_HIBERNATE_DDL_AUTO` | Hibernate schema management | `update` |
| `SPRING_JPA_SHOW_SQL` | Log SQL queries | `false` |
| `SPRING_JPA_HIBERNATE_DIALECT` | Hibernate dialect | `org.hibernate.dialect.PostgreSQLDialect` |
| `JWT_SECRET` | Base64-encoded HMAC-SHA256 secret key for signing JWTs | *(Required)* |
| `JWT_ACCESS_TOKEN_EXPIRATION` | Access token validity in milliseconds | `1800000` (30 minutes) |
| `JWT_REFRESH_TOKEN_EXPIRATION` | Refresh token validity in milliseconds | `604800000` (7 days) |
| `APP_CORS_ALLOWED_ORIGINS` | Comma-separated allowed CORS origins | `http://localhost:3000` |

---

## License

Unlicensed — Private Project.
