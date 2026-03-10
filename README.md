# GymRat Backend

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.7-6DB33F?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-4479A1?logo=mysql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-ready-2496ED?logo=docker&logoColor=white)
![CI](https://github.com/hajisan/gymrat/actions/workflows/java.yaml/badge.svg)
![Release](https://img.shields.io/github/v/release/hajisan/gymrat)
![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)

A personal workout tracking REST API built with Spring Boot. Track training sessions, log exercises, and record sets with weight, reps, and duration data.

## Tech Stack

- **Java 21** / **Spring Boot 3.5.7**
- **Spring Security 6** — form-based auth with remember-me
- **Spring Data JPA** / **Hibernate** — ORM
- **MySQL 8.0+** — production database
- **H2** — in-memory database for tests
- **Thymeleaf** — server-side login page
- **Docker** — containerized deployment
- **GitHub Actions** — CI/CD to DigitalOcean

## Project Structure

```
src/main/java/com/example/gymrat_backend/
├── config/          # Security, CORS, Web config
├── controller/      # REST controllers + MVC login
├── service/         # Business logic (interface + impl)
├── repository/      # Spring Data JPA repositories
├── model/           # JPA entities + enums
├── dto/             # Request/response DTOs
├── mapper/          # Entity ↔ DTO mappers
└── exception/       # Custom exceptions
```

## Domain Model

```
Exercise ──→ PerformedExercise ←── TrainingSession
                    │
                    ↓
              PerformedSet
```

| Entity | Description |
|---|---|
| `Exercise` | Master catalog of exercises (name, muscle group, equipment, type) |
| `TrainingSession` | A single workout session with timestamps and notes |
| `PerformedExercise` | An exercise added to a session, with ordering |
| `PerformedSet` | A single set (reps, weight, duration, side of body, notes) |

**Enums:**
- `ExerciseType`: `REP_BASED`, `DURATION_BASED`, `BOTH`
- `SideOfBody`: `LEFT`, `RIGHT`, `BOTH`

## API Endpoints

All endpoints are prefixed with `/api` and require authentication.

### Exercises — `/api/exercises`

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/exercises` | Get all exercises |
| `GET` | `/api/exercises/{id}` | Get exercise by ID |
| `POST` | `/api/exercises` | Create exercise |
| `PUT` | `/api/exercises/{id}` | Update exercise |
| `DELETE` | `/api/exercises/{id}` | Delete exercise |

### Workouts — `/api/workout`

| Method | Path | Description |
|---|---|---|
| `POST` | `/api/workout/start` | Start a new workout session |
| `GET` | `/api/workout/{sessionId}` | Get active workout |
| `POST` | `/api/workout/{sessionId}/exercises` | Add exercise to workout |
| `DELETE` | `/api/workout/{sessionId}/exercises/{performedExerciseId}` | Remove exercise |
| `POST` | `/api/workout/{sessionId}/sets` | Log or update a set |
| `DELETE` | `/api/workout/{sessionId}/sets/{performedSetId}` | Delete a set |
| `POST` | `/api/workout/{sessionId}/complete` | Complete the workout |
| `DELETE` | `/api/workout/{sessionId}` | Delete a workout session |
| `GET` | `/api/workout/history` | Get all training sessions (summary) |
| `GET` | `/api/workout/history/detailed` | Get all sessions with full exercises and sets data |
| `GET` | `/api/workout/history/paged` | Get sessions with pagination (`page`, `size`) |
| `GET` | `/api/workout/exercises/{exerciseId}/last-performed` | Get last performance data for an exercise |

### Dashboard — `/api/home`

| Method | Path | Description |
|---|---|---|
| `GET` | `/api/home/summary` | Weekly stats and last workout summary |

## Security

- Single-user form-based authentication
- Credentials configured via environment variables (`AUTH_USERNAME`, `AUTH_PASSWORD`)
- BCrypt password hashing
- Remember-me cookie with 90-day validity
- CSRF protection with exceptions for API endpoints
- Public access: `/login`, `/css/**`, `/js/**`, `/icons/**`

## Configuration Profiles

| Profile | Database | DDL | SQL Logging |
|---|---|---|---|
| `dev` | MySQL (via env vars) | `update` | DEBUG |
| `prod` | MySQL (via env vars) | `validate` | WARN |
| `test` | H2 in-memory | `create-drop` | off |

## Environment Variables

### Development (`dev` profile)

```env
SPRING_PROFILES_ACTIVE=dev
DEV_DB_URL=jdbc:mysql://localhost:3306/gymrat_db
DEV_DB_USERNAME=your_user
DEV_DB_PASSWORD=your_password
AUTH_USERNAME=admin
AUTH_PASSWORD=admin
```

### Production (`prod` profile)

```env
SPRING_PROFILES_ACTIVE=prod
PROD_DB_URL=jdbc:mysql://host:3306/gymrat_db
PROD_DB_USERNAME=your_user
PROD_DB_PASSWORD=your_password
AUTH_USERNAME=your_admin_user
AUTH_PASSWORD=your_secure_password
CORS_ALLOWED_ORIGINS=https://yourdomain.com
```

## Running Locally

**Prerequisites:** Java 21, Maven, MySQL

```bash
# Clone the repo
git clone https://github.com/hajisan/gymrat.git
cd gymrat_backend

# Set environment variables (see above)

# Run with dev profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The API will be available at `http://localhost:8080`.

## Running with Docker

```bash
# Development (builds from source)
docker-compose up --build

# Production (uses pre-built image from GHCR)
docker-compose -f docker-compose.prod.yml up -d
```

The Docker image is published to GitHub Container Registry: `ghcr.io/hajisan/gymrat:latest`

**Docker environment:**
- Base image: `eclipse-temurin:21-jre-alpine`
- Runs as non-root user (`gymrat`, UID 1001)
- JVM tuned for 1 GB RAM: `-Xms256m -Xmx512m -XX:+UseG1GC`
- Health check: `GET /login` every 30 seconds
- Port: `8080`

## CI/CD

Two GitHub Actions workflows:

| Workflow | Trigger | Action |
|---|---|---|
| `java.yaml` | Push/PR to `main` | Runs `mvn test` |
| `deploy.yml` | Push to `main` | Builds & pushes Docker image to GHCR, then SSHs into DigitalOcean droplet and redeploys |

**Required GitHub secrets for deployment:**
- `DROPLET_HOST` — server IP
- `DROPLET_USER` — SSH username
- `DROPLET_SSH_KEY` — SSH private key

## Running Tests

```bash
./mvnw test
```

Tests use the `test` Spring profile with an H2 in-memory database — no external dependencies required.
