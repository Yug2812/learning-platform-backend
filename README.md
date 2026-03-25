# AI-Assisted Online Learning Support Platform - Backend

This is the Spring Boot 3 backend for the learning platform.

## Features Implemented in Phase 1
- **Project Scaffolding**: Java 17, Spring Boot 3.4.
- **Database**: H2 (in-memory) configured for local development. PostgreSQL ready for production.
- **Security**: JWT-based authentication and role-based access control (Student / Admin).
- **Domain Models**: User, Role, Course, Topic, Question, QuizAttempt, Result, Recommendation.
- **APIs**:
  - `POST /api/auth/register` - Create a new user (`name`, `email`, `password`, `roles`).
  - `POST /api/auth/login` - Authenticate users with JWT response.
  - `GET /api/auth/me` - Get current authenticated user details.

## Default Data
When the backend starts, default roles (`ROLE_STUDENT` and `ROLE_ADMIN`) are automatically seeded into the database via `DataSeeder.java`.

## How to Run Locally

### Prerequisites
- JDK 17 or higher
- Maven (optional, wrapper is included)

### Steps
1. Navigate to the `backend` directory:
   ```bash
   cd backend
   ```
2. Run the application using the Maven wrapper:
   ```bash
   ./mvnw spring-boot:run
   ```
3. The server will start on `http://localhost:8080`.

### Database Console
- You can view the H2 database at `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:learningdb`
- **Username**: `sa`
- **Password**: *(leave blank)*

## API Testing (Postman)
1. **Register**: Send POST request to `http://localhost:8080/api/auth/register`
   ```json
   {
       "name": "Jane Doe",
       "email": "jane@example.com",
       "password": "password123",
       "roles": ["student"]
   }
   ```
2. **Login**: Send POST request to `http://localhost:8080/api/auth/login` with email and password to receive the JWT Token.
3. **Authentication**: Use the token from login in the `Authorization` header as `Bearer <token>` for authenticated endpoints.
