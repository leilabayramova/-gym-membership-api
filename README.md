# 🏋️ Gym Membership API

Gym Membership API is a Spring Boot REST API for managing gym members, trainers, training programs, subscriptions, authentication, authorization, enrollments, and trainer documents.

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
- Spring Cache
- Spring Scheduling
- Spring Async
- Gradle
- Lombok
- Jakarta Validation
- Swagger / OpenAPI
- JUnit 5
- Mockito
- H2 Database for integration testing

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
├── scheduler
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
- **Scheduler** — contains scheduled background tasks
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

```java
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

```http
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

Spring Security authorities are represented as:

```text
ROLE_USER
ROLE_ADMIN
```

---

## Password Security

Passwords are encoded using:

```java
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

```http
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

```http
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

```java
SessionCreationPolicy.STATELESS
```

---

## Role-Based Access Control

Different permissions are provided for USER and ADMIN roles.

| Operation | USER | ADMIN |
|---|---|---|
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

```java
.hasRole("ADMIN")
```

and:

```java
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

# 🗄 Week 3 — Database Connections and Advanced Queries

Week 3 focused on advanced database relationships, querying, transaction management, integration testing, and query optimization.

Implemented:

- Many-to-Many relationship
- Category entity
- Advanced JPQL query
- Dynamic filtering using Spring Data JPA Specifications
- Transactional enrollment
- Transaction rollback
- N+1 query detection
- Query optimization using `@EntityGraph`
- H2 integration testing

---

## Many-to-Many Relationship

A new Category entity was introduced.

Training programs and categories have a Many-to-Many relationship.

```text
TrainingProgram
       ↕
    Category
```

A training program can belong to multiple categories.

A category can contain multiple training programs.

Example categories:

```text
CARDIO
STRENGTH
FLEXIBILITY
```

The relationship is stored using the join table:

```text
training_program_categories
```

---

## Advanced JPQL Query

A custom JPQL query was implemented for filtering training programs using multiple database conditions.

Supported filters include:

- Trainer ID
- Category ID
- Minimum monthly price
- Maximum monthly price
- Maximum duration in weeks

Example endpoint:

```http
GET /api/training-programs/filter
```

This demonstrates the use of custom JPQL queries for more advanced database operations.

---

## Dynamic Filtering with Specifications

Spring Data JPA Specifications were implemented to support dynamic and optional filtering.

Supported filters include:

```text
name
trainerId
categoryId
minPrice
maxPrice
maxDurationInWeeks
```

Example endpoint:

```http
GET /api/training-programs/search
```

Only the supplied parameters are included in the generated database query.

This allows flexible searching without creating a separate repository method for every possible filter combination.

---

## Transactional Enrollment

A new enrollment process was implemented using:

```java
@Transactional
```

The enrollment flow:

```text
Create Member
     ↓
Find Training Program
     ↓
Create Subscription
     ↓
Return Enrollment Response
```

Member creation and subscription creation are executed inside the same transaction.

If subscription creation fails after the member has already been saved, the complete transaction is rolled back.

This prevents incomplete enrollment data from remaining in the database.

---

## N+1 Query Optimization

An N+1 query problem was detected while loading training programs together with their trainer and categories.

The issue was optimized using:

```java
@EntityGraph
```

Optimized endpoint:

```http
GET /api/training-programs/optimized
```

The required related entities are loaded efficiently instead of executing repeated queries for every training program.

---

## Transaction Rollback Integration Test

An integration test was implemented using H2.

The test intentionally causes subscription persistence to fail after member creation.

The test verifies that:

```text
Member count before == Member count after

Subscription count before == Subscription count after
```

This confirms that transaction rollback works correctly.

---

# ⚙️ Week 4 — Advanced Spring Features

Week 4 focused on advanced Spring Boot features including caching, file management, scheduling, asynchronous processing, external configuration, and improved Swagger documentation.

Implemented:

- Spring Cache
- Cache invalidation
- Trainer document upload
- Trainer document download
- File type validation
- File size validation
- Scheduled subscription expiration
- Asynchronous enrollment notification
- Environment-specific configuration
- Swagger / OpenAPI documentation improvements

---

## Spring Cache

Caching was implemented for training program details using:

```java
@Cacheable
```

Example:

```java
@Cacheable(value = "trainingPrograms", key = "#id")
```

The first request retrieves the training program from the database.

Repeated requests for the same training program use the cached result instead of executing another training program query.

The cache behavior was verified through Hibernate SQL logs.

---

## Cache Invalidation

Cached training program data is removed when a training program is updated or deleted.

This is implemented using:

```java
@CacheEvict
```

Example:

```java
@CacheEvict(value = "trainingPrograms", key = "#id")
```

This prevents outdated training program data from remaining in the cache after modifications.

After an update, the next GET request retrieves fresh data from the database and caches it again.

---

## Trainer Document Upload and Download

Trainer-specific document management was implemented for trainer certificates and qualification documents.

Supported file types:

```text
PDF
PNG
JPEG
```

Maximum allowed business file size:

```text
5 MB
```

### Upload Endpoint

```http
POST /api/trainers/{trainerId}/documents
```

### Download Endpoint

```http
GET /api/trainers/{trainerId}/documents/{fileName}
```

Validation includes:

- Empty file validation
- File type validation
- File size validation
- Trainer existence validation
- Document existence validation

Possible upload responses include:

```text
200 OK
400 Bad Request
404 Not Found
413 Payload Too Large
```

Uploaded files are stored in trainer-specific directories.

Example:

```text
uploads/trainers/{trainerId}/
```

---

## Scheduled Subscription Expiration

Spring Scheduling was implemented to automatically deactivate expired subscriptions.

The scheduler runs daily at midnight:

```java
@Scheduled(cron = "0 0 0 * * *")
```

The scheduled process finds subscriptions where:

```text
active = true
AND
endDate < current date
```

Expired subscriptions are automatically changed to:

```text
active = false
```

The number of deactivated subscriptions is logged.

The scheduler was tested using a temporary short execution interval before restoring the daily schedule.

---

## Asynchronous Processing

Asynchronous enrollment notification processing was implemented using:

```java
@Async
```

After a successful enrollment, notification processing runs on a separate thread.

Flow:

```text
Enrollment Request
       ↓
Member + Subscription Saved
       ↓
Enrollment Response
       ↓
Async Notification Processing
```

This prevents notification processing from blocking the main enrollment request.

The asynchronous execution was verified through application logs running on a separate task thread.

---

## External Configuration and Spring Profiles

Environment-specific configuration was introduced for development and production environments.

The application supports:

```text
dev
prod
```

Production configuration uses environment variables such as:

```text
DB_URL
DB_USERNAME
DB_PASSWORD
JWT_SECRET
JWT_EXPIRATION
SERVER_PORT
```

This keeps production credentials outside the source code.

Sensitive local configuration is not committed to Git.

The local:

```text
application.yaml
```

is excluded through `.gitignore`.

The repository contains:

```text
application-example.yaml
```

which can be used as a template for local configuration.

---

## Swagger / OpenAPI Improvements

Swagger documentation was improved for the new trainer document functionality.

A dedicated Swagger section was added:

```text
Trainer Documents
```

The documentation includes:

- Endpoint summaries
- Endpoint descriptions
- Authentication requirements
- Supported file constraints
- Successful responses
- Validation errors
- File-size errors
- Not-found responses

Upload responses are documented as:

```text
200 — Document uploaded successfully
400 — Unsupported or invalid file
404 — Trainer not found
413 — File size exceeds 5 MB
```

Download responses are documented as:

```text
200 — Document downloaded successfully
404 — Trainer or document not found
```

---

# ⚙️ Configuration

Create your local:

```text
src/main/resources/application.yaml
```

The local configuration file is excluded from Git because it may contain sensitive credentials.

Use:

```text
application-example.yaml
```

as a template.

Example local configuration:

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

Sensitive credentials should never be committed to the repository.

---

# 🗄 Database

The project uses PostgreSQL.

Database name:

```text
gym_membership_db
```

Main tables include:

```text
members
trainers
training_programs
subscriptions
users
categories
training_program_categories
```

Hibernate is used for ORM and database interaction.

For local development, schema updates can be managed using:

```text
ddl-auto: update
```

---

# ▶️ Running the Project

## 1. Clone the Repository

```bash
git clone https://github.com/leilabayramova/-gym-membership-api.git
```

---

## 2. Create the Database

```sql
CREATE DATABASE gym_membership_db;
```

---

## 3. Configure PostgreSQL

Create:

```text
src/main/resources/application.yaml
```

using:

```text
application-example.yaml
```

as a template.

Update the PostgreSQL username and password with your local credentials.

---

## 4. Run Tests

### Windows

```bash
.\gradlew test
```

### macOS / Linux

```bash
./gradlew test
```

---

## 5. Run the Application

### Windows

```bash
.\gradlew bootRun
```

### macOS / Linux

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

# 🧪 Testing

The project contains both unit and integration tests.

Unit tests use:

- JUnit 5
- Mockito

Integration testing uses:

- Spring Boot Test
- H2 Database

Testing covers areas such as:

- Service-layer business logic
- JWT generation and validation
- Token expiration
- Transaction rollback
- Database behavior

Run all tests using:

```bash
.\gradlew test
```

---

# 📖 Swagger Authentication Flow

Swagger can be used to test secured endpoints.

Flow:

```text
Register / Login
        ↓
Receive JWT Token
        ↓
Open Swagger
        ↓
Click Authorize
        ↓
Enter JWT Token
        ↓
Call Protected Endpoint
```

Swagger automatically sends:

```text
Authorization: Bearer <token>
```

---

# 📅 Project Progress

| Week | Topic | Status |
|---|---|---|
| Week 1 | REST API Development | ✅ Completed |
| Week 2 | JWT Authentication and Authorization | ✅ Completed |
| Week 3 | Database Connections and Advanced Queries | ✅ Completed |
| Week 4 | Advanced Spring Features | ✅ Completed |

---

# ✅ Current Features

The project currently demonstrates:

- REST API development
- Layered architecture
- PostgreSQL integration
- Entity relationships
- One-to-Many relationships
- Many-to-Many relationships
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
- Integration testing
- Spring Security
- BCrypt password hashing
- JWT authentication
- JWT token expiration
- Stateless security
- Role-based authorization
- 401 / 403 handling
- Advanced JPQL queries
- Dynamic filtering using Specifications
- Transaction management
- Transaction rollback
- N+1 query optimization
- EntityGraph
- H2 integration testing
- Spring Cache
- Cache invalidation
- Multipart file upload
- File download
- File validation
- Scheduled tasks
- Asynchronous processing
- Spring profiles
- External configuration
- Swagger / OpenAPI improvements

---

# 👩‍💻 Author

**Leyla Bayramova**

Gym Membership API — Internship Project
