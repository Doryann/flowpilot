# FlowPilot

FlowPilot is a full-stack technical showcase project designed to demonstrate modern web application development with multiple frontend and backend technologies.

The goal of this repository is to build the same business-oriented application using different technical stacks, while keeping a clean architecture, clear documentation, and production-oriented practices.

## Objectives

- Build a modern full-stack application.
- Demonstrate Angular and React frontend implementations.
- Demonstrate Spring Boot and Quarkus backend implementations.
- Use PostgreSQL as the main database.
- Use OpenAPI as a shared API contract.
- Explore clean architecture, MVC, CQRS, and event-driven concepts.
- Provide a professional portfolio project with documentation and reproducible setup.

## Repository structure

```text
flowpilot/
├── backend/
│   ├── springboot/
│   └── quarkus/
│
├── frontend/
│   ├── angular/
│   └── react/
│
├── openapi/
│   └── flowpilot-api.yaml
│
├── docker/
│   ├── postgres/
│   └── scripts/
│
├── docs/
│   ├── architecture.md
│   ├── decisions/
│   └── screenshots/
│
├── .github/
│   └── workflows/
│
├── docker-compose.yml
├── .gitignore
└── README.md
```

## Local ports

| Service | URL |
|---|---|
| Angular frontend | http://localhost:4200 |
| Spring Boot backend | http://localhost:8080 |
| Quarkus backend | http://localhost:8081 |
| React frontend | http://localhost:5173 |
| PostgreSQL | localhost:5432 |

## Run with Docker

Build and start the full local environment:

```bash
docker compose up --build
```

Run in detached mode:

```bash
docker compose up --build -d
```

Stop all services:

```bash
docker compose down
```

Avoid conflicts:
```bash
docker stop flowpilot-postgres
docker rm flowpilot-postgres
```

## Run in development mode

For development, FlowPilot provides a dedicated Docker Compose override file:

```bash
docker compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml up --build
```

This mode is intended to run services with live reload when possible.

Currently, the Quarkus backend runs in development mode with hot reload enabled:

```bash
docker compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml up --build backend-quarkus
```

When a Java file is modified in:

```text
backend/quarkus/src/
```

Quarkus automatically recompiles the application on the next HTTP request.

The development compose file is expected to progressively include the frontend applications as well, so Angular and React can also run with live reload through Docker.

Target development behavior:

```text
backend/quarkus     → Quarkus dev mode with hot reload
frontend/angular    → Angular dev server with live reload
frontend/react      → React dev server with live reload
```

Stop the development environment:

```bash
docker compose -f docker/docker-compose.yml -f docker/docker-compose.dev.yml down --remove-orphans
```

## Technologies

FlowPilot is built as a multi-stack full-stack project.

### Frontend

- Angular
- React
- TypeScript
- Tailwind CSS
- Internationalization
- Responsive design

### Backend

- Spring Boot
- Quarkus
- Java 21
- REST APIs
- OpenAPI / Swagger
- Validation
- Clean architecture principles

### Database

- PostgreSQL

### Architecture concepts

- MVC
- Clean architecture
- CQRS
- Event-driven architecture
- API-first development with OpenAPI

### DevOps and tooling

- Docker
- Docker Compose
- Podman-compatible setup
- Maven
- GitHub
- GitHub Actions
- IntelliJ IDEA

### Planned additions

- React frontend implementation
- Quarkus persistence layer
- Shared OpenAPI contract generation
- CI/CD pipeline
- Automated tests
- CQRS and event-driven workflows
- Production-like deployment documentation