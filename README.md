# 🏋️ Gym Membership API

Gym Membership API is a Spring Boot REST API for managing gym members, trainers, training programs, subscriptions, authentication, and authorization.

The project is developed step by step as part of an internship program.

---

## 🛠 Technologies

- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Hibernate
- Spring Security
- JWT
- Gradle
- Lombok
- Jakarta Validation
- Swagger / OpenAPI
- JUnit 5
- Mockito

---

## 🏗 Project Structure

The project follows a layered architecture:

```text
src/main/java/com/example/gymmembershipapi
│
├── config
├── controller
├── dto
├── entity
├── exception
├── mapper
├── repository
├── security
└── service
```

Main responsibilities:

- **Controller** — handles HTTP requests
- **Service** — contains business logic
- **Repository** — communicates with the database
- **Entity** — represents database models
- **DTO** — handles request and response data
- **Mapper** — converts Entity and DTO objects
- **Exception** — handles application errors
- **Security** — contains JWT and authentication logic
- **Config** — contains application configuration

---

# ✅ Week 1 — REST API Development

Week 1 focused on developing the core Gym Membership management system.

## Main Features

Implemented:

- Member CRUD operations
- Trainer CRUD operations
- Training Program CRUD operations
- Subscription management
- Entity relationships
- DTO and Mapper layers
- Request validation
- Global exception handling
- Pagination and sorting
- Subscription soft delete
- Trainer deletion protection
- Swagger / OpenAPI documentation
- Unit tests

---

## Main Entities

The project contains the following main entities:

```text
Member
Trainer
TrainingProgram
Subscription
```

### Relationships

```text
Trainer
   ↓
Training Programs
   ↓
Subscriptions
   ↑
Member
```

A trainer can have multiple training programs.

A member can have multiple subscriptions.

A training program can also be connected to multiple subscriptions.

---

## Subscription Soft Delete

Subscriptions are not permanently deleted from the database.

Instead:

```text
active = false
```

is used.

This allows subscription history to be preserved.

---

## Trainer Deletion Protection

A trainer cannot be deleted while related training programs exist.

Instead of automatically deleting related training programs, the API blocks the operation and returns:

```text
409 Conflict
```

This prevents accidental deletion of related data and subscription history.

---

## Validation

Request DTOs use Jakarta Validation.

Validation is used for:

- Required fields
- Email format
- Numeric constraints
- String validation
- Business-related input rules

---

## Exception Handling

Global exception handling is implemented using:

```text
@RestControllerAdvice
```

Custom exceptions include:

```text
ResourceNotFoundException
DuplicateResourceException
ResourceInUseException
```

The API returns consistent error responses.

---

## Pagination and Sorting

List endpoints support pagination and sorting.

Example:

```text
GET /api/trainers?page=0&size=10&sortBy=id&direction=asc
```

---

## Swagger / OpenAPI

Swagger is used for API documentation and manual endpoint testing.

Swagger UI:

```text
http://localhost:8383/swagger-ui/index.html
```

---

## Unit Testing

Service-layer tests were implemented using:

- JUnit 5
- Mockito

Test classes include:

```text
MemberServiceTest
TrainerServiceTest
TrainingProgramServiceTest
SubscriptionServiceTest
```

---

# 🔐 Week 2 — JWT Authentication and Authorization

Week 2 focused on securing the API using Spring Security and JWT.

Implemented:

- User entity
- USER and ADMIN roles
- BCrypt password hashing
- Registration endpoint
- Login endpoint
- JWT token generation
- JWT validation
- JWT authentication filter
- Stateless authentication
- Protected endpoints
- Role-based access control
- Custom 401 Unauthorized response
- Custom 403 Forbidden response
- JWT token expiration
- JWT unit tests
- Swagger Bearer authentication

---

## User Roles

The application currently supports two roles:

```text
USER
ADMIN
```

Newly registered users receive:

```text
USER
```

by default.

---

## Password Security

Passwords are encoded using:

```text
BCryptPasswordEncoder
```

Passwords are never stored as plain text in the database.

Flow:

```text
Raw Password
     ↓
BCrypt
     ↓
Encoded Password
     ↓
PostgreSQL
```

---

## Authentication

### Register

```text
POST /api/auth/register
```

Example request:

```json
{
  "email": "user@example.com",
  "password": "StrongPass123!"
}
```

### Login

```text
POST /api/auth/login
```

Example request:

```json
{
  "email": "user@example.com",
  "password": "StrongPass123!"
}
```

After successful login, the API returns a JWT access token.

Example response:

```json
{
  "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
  "tokenType": "Bearer",
  "email": "user@example.com",
  "role": "USER"
}
```

---

## JWT Authentication

Protected requests use:

```text
Authorization: Bearer <token>
```

Authentication flow:

```text
Login
  ↓
JWT Generated
  ↓
Client Sends Token
  ↓
JwtAuthenticationFilter
  ↓
Token Validation
  ↓
User Loaded
  ↓
SecurityContext
  ↓
Protected Endpoint
```

The application uses stateless authentication:

```text
SessionCreationPolicy.STATELESS
```

---

## Role-Based Access Control

Different permissions are provided for USER and ADMIN roles.

| Operation | USER | ADMIN |
|---|:---:|:---:|
| View Trainers | ✅ | ✅ |
| Create Trainer | ❌ | ✅ |
| Update Trainer | ❌ | ✅ |
| Delete Trainer | ❌ | ✅ |
| View Members | ✅ | ✅ |
| Manage Members | ❌ | ✅ |
| View Training Programs | ✅ | ✅ |
| Manage Training Programs | ❌ | ✅ |
| Subscription Operations | ✅ | ✅ |

Authorization rules use:

```text
.hasRole("ADMIN")
```

and:

```text
.hasAnyRole("USER", "ADMIN")
```

---

## Local ADMIN Testing

Public registration creates users with the USER role.

For local authorization testing, a registered user can be promoted to ADMIN directly in PostgreSQL:

```sql
UPDATE users
SET role = 'ADMIN'
WHERE email = 'admin@example.com';
```

After changing the role, the user logs in again and receives a new JWT token.

---

## 401 and 403 Responses

Custom Spring Security handlers were implemented.

### 401 Unauthorized

Returned when authentication is missing or invalid.

Examples:

```text
No token
Invalid token
Expired token
```

### 403 Forbidden

Returned when the user is authenticated but does not have the required role.

Example:

```text
USER → POST /api/trainers → 403 Forbidden
```

---

## JWT Token Expiration

JWT tokens have an expiration time.

Current default configuration:

```yaml
jwt:
  secret: ${JWT_SECRET:your-secret}
  expiration: ${JWT_EXPIRATION:3600000}
```

Default token lifetime:

```text
1 hour
```

Expired tokens return:

```text
401 Unauthorized
```

Token expiration was tested both manually through Swagger and through unit tests.

---

## JWT Tests

JWT tests verify:

- Token generation
- Email extraction
- Role extraction
- Valid token verification
- Expired token rejection

Test class:

```text
JwtServiceTest
```

---

## Swagger Bearer Authentication

Swagger supports JWT authentication.

Steps:

1. Register or login
2. Copy the returned access token
3. Click **Authorize**
4. Paste the JWT token
5. Call protected endpoints

Swagger automatically sends:

```text
Authorization: Bearer <token>
```

---

# ⚙️ Configuration

Create your local `application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/gym_membership_db
    username: postgres
    password: your_password

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8383

jwt:
  secret: ${JWT_SECRET:your-secret}
  expiration: ${JWT_EXPIRATION:3600000}
```

Sensitive credentials should not be committed to the repository.

An `application-example.yml` file can be used to show the required configuration.

---

# 🗄 Database

The project uses PostgreSQL.

Database name:

```text
gym_membership_db
```

Main tables:

```text
members
trainers
training_programs
subscriptions
users
```

Hibernate currently manages schema updates using:

```yaml
ddl-auto: update
```

---

# ▶️ Running the Project

## 1. Create the database

```sql
CREATE DATABASE gym_membership_db;
```

## 2. Configure PostgreSQL

Update your local `application.yml` with your PostgreSQL credentials.

## 3. Run tests

Windows:

```bash
.\gradlew test
```

macOS / Linux:

```bash
./gradlew test
```

## 4. Run the application

Windows:

```bash
.\gradlew bootRun
```

macOS / Linux:

```bash
./gradlew bootRun
```

Application:

```text
http://localhost:8383
```

Swagger:

```text
http://localhost:8383/swagger-ui/index.html
```

---

# 📅 Project Progress

| Week | Topic | Status |
|---|---|---|
| Week 1 | REST API Development | ✅ Completed |
| Week 2 | JWT Security | ✅ Completed |
| Week 3 | Upcoming | ⏳ Pending |
| Week 4 | Upcoming | ⏳ Pending |

---

# 🚀 Week 3

Week 3 requirements will be added here.

---

# 🚀 Week 4

Week 4 requirements will be added here.

---

# ✅ Current Features

The project currently demonstrates:

- REST API development
- Layered architecture
- PostgreSQL integration
- Entity relationships
- DTO pattern
- Mapper pattern
- Validation
- Global exception handling
- Pagination
- Sorting
- Soft delete
- Business rule validation
- Swagger documentation
- Unit testing
- Spring Security
- BCrypt password hashing
- JWT authentication
- Stateless security
- Role-based authorization
- 401 / 403 handling
- JWT token expiration