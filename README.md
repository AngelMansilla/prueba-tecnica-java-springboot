# EAN Service - Mercadona Technical Test

This project is a RESTful API built with Spring Boot that manages Products, Providers, and Destinations, with a specific focus on EAN (European Article Number) logic and validation.

## 🚀 Refactorings & Improvements

This project has been updated to follow modern Spring Boot standards and best practices:

- **Constructor Injection**: Replaced field-level `@Autowired` with constructor-based dependency injection for better testability and immutability.
- **Improved Testing**: 
    - Transitioned from integration-heavy tests to efficient unit tests using `MockitoExtension`.
    - Resolved Java 25 compatibility issues with Mockito/ByteBuddy.
    - Achieved 100% pass rate in the test suite (51 tests).
- **Dependency Management**: Cleaned up `pom.xml` to leverage Spring Boot's BOM (Bill of Materials) for dependency version management, specifically for Jakarta APIs.
- **Code Quality**:
    - Standardized error handling via `GlobalExceptionHandler`.
    - Optimized service layer logic (e.g., removing redundant `Optional` wrappers).
    - Added validation support via `spring-boot-starter-validation`.

## 🛠️ Technology Stack

- **Java**: 25 (with ByteBuddy experimental support)
- **Spring Boot**: 3.3.1
- **Security**: Spring Security + JWT
- **Database**: H2 (In-memory) + JPA/Hibernate
- **Database Migrations**: Flyway
- **Caching**: Caffeine
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **Testing**: JUnit 5, Mockito, MockMvc

## 🏃 How to Run

1. **Prerequisites**: Ensure you have JDK 25 and Maven installed.
2. **Build**:
   ```bash
   mvn clean install
   ```
3. **Run**:
   ```bash
   mvn spring-boot:run
   ```
4. **Access Swagger UI**: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

## 🧪 Running Tests

To run the complete test suite:
```bash
mvn test
```

> [!NOTE]
> The project uses an experimental ByteBuddy flag in `pom.xml` to ensure Mockito compatibility with Java 25.

## 📁 Project Structure

- `com.mercadona.eanservice.controller`: REST Controllers.
- `com.mercadona.eanservice.service`: Business logic interfaces.
- `com.mercadona.eanservice.service.impl`: Implementation of business logic.
- `com.mercadona.eanservice.repository`: Data access layer.
- `com.mercadona.eanservice.model`: JPA Entities.
- `com.mercadona.eanservice.dto`: Data Transfer Objects.
- `com.mercadona.eanservice.security`: Security configuration and JWT handling.
- `com.mercadona.eanservice.exception`: Exception handling.

## 📋 API Endpoints Summary

- `POST /auth/login`: Authenticate and receive a JWT.
- `GET /api/products`: List all products.
- `GET /api/products/{id}`: Get product by ID (includes EAN specific logic).
- `GET /api/products/ean/{ean}`: Get product by EAN code.
- `GET /api/destinations`: List destinations.
- `GET /api/providers`: List providers.

---
*Created as part of the Mercadona Technical Evaluation.*
