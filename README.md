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