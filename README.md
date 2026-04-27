# Fake Job Detector API

A REST API that analyzes job descriptions and detects fake/scam jobs using rule-based scoring engine.

## Tech Stack
- Java 17 + Spring Boot 3.x
- Spring Security + JWT Authentication
- MySQL + Spring Data JPA
- Swagger UI (API Documentation)
- AWS EC2 + RDS (Deployment)

## Features
- JWT Authentication (Register/Login)
- Role-based access (USER / ADMIN)
- Rule-based fake job scoring engine (8 red flag rules)
- Job analysis history
- Community reporting system
- Company blacklist management
- Leaderboard of most reported companies
- Swagger UI documentation

## API Endpoints

### Auth (Public)
- POST `/api/auth/register` — Register new account
- POST `/api/auth/login` — Login and get JWT token

### Jobs (Secured)
- POST `/api/jobs/analyze` — Analyze a job description
- GET `/api/jobs/history` — View your analysis history

### Reports
- POST `/api/reports` — Report a job as fake
- GET `/api/reports/leaderboard` — Most reported companies (public)

### Admin Only
- GET `/api/admin/users` — All users
- GET `/api/admin/reports` — All reports
- POST `/api/admin/blacklist` — Add company to blacklist
- DELETE `/api/admin/blacklist/{id}` — Remove from blacklist
- GET `/api/admin/blacklist` — View blacklist

## Scoring Engine — Red Flag Rules

| Rule | Points |
|------|--------|
| Unrealistic earning claims | +20 |
| Urgency language | +15 |
| Personal email (Gmail/Yahoo) | +15 |
| Asks for documents/money | +20 |
| Vague description | +5 |
| No company name | +10 |
| Excessive capitals | +10 |
| Blacklisted company | +20 |

## Verdict
- 0-30 → LEGIT
- 31-60 → SUSPICIOUS
- 61-100 → FAKE

## Setup

1. Clone the repo
```bash
git clone https://github.com/Kartikjindal99/fake-job-detector.git
```

2. Copy example properties
```bash
cp src/main/resources/application.example.properties src/main/resources/application.properties
```

3. Fill in your values in `application.properties`

4. Run MySQL and create database
```sql
CREATE DATABASE fakejobdetector;
```

5. Run the application
```bash
./mvnw spring-boot:run
```

6. Open Swagger UI
```
http://65.1.92.223:8080/swagger-ui/swagger-ui/index.html#/
```

## API Documentation
Swagger UI available at: `http://65.1.92.223:8080/swagger-ui/swagger-ui/index.html#/`
