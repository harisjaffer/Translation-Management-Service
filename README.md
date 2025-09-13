# Translation Management API

A scalable **API-driven service** for managing multilingual translations, optimized for performance and large datasets. Designed for frontend applications like **Vue.js** and following **SOLID principles**.

---

## Features

- Store translations for multiple locales (`en`, `fr`, `es`) and easily add new languages.
- Tag translations for context (e.g., `mobile`, `desktop`, `web`).
- Expose endpoints to **create, update, view, and search** translations by tag, key, or content.
- Provide a **JSON export endpoint** for frontend applications, always returning updated translations.
- Token-based authentication to secure APIs.
- Optimized for high performance: all endpoints respond in **< 200ms**; export endpoint handles 100k+ records efficiently.

---

## Technical Details

- **Java Version:** 8
- **Frameworks:** Spring Boot, Spring Data JPA
- **Database:** Any relational database (PostgreSQL recommended)
- **Streaming:** Efficient streaming of large datasets using `StreamingResponseBody`
- **Serialization:** Jackson `JavaTimeModule` for `Instant` support
- **Security:** JWT token-based authentication
- **Design:** Follows **SOLID principles**, optimized SQL queries

---
## Swagger Documentation
This project includes Swagger UI for interactive API documentation.

After starting the application, access Swagger UI:
- http://localhost:8086/swagger-ui/index.html
- You will see all endpoints listed with request parameters, request bodies, and response schemas.
- You can test API requests directly from Swagger UI:
- Add your Bearer JWT token in the “Authorize” button.
- Fill query parameters or request bodies as required.
- Execute requests and view responses directly.

Tip: For large datasets (100k+ records), avoid using Swagger UI for the export endpoint to prevent freezing. Use curl or a direct HTTP client instead.

Configure Database

spring.datasource.url=jdbc:postgresql://localhost:5432/translations
spring.datasource.username=translations
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update

Build & Run
mvn clean install
mvn spring-boot:run

Access Swagger UI
http://localhost:8086/swagger-ui.html

Performance Notes
JSON export endpoint uses streaming to handle large datasets efficiently.

For very large exports (100k+ records), use curl or a direct HTTP client instead of Swagger UI to avoid freezing.

Pagination or batch processing is recommended for frontend applications if loading all translations at once is not required.

Dependencies
Spring Boot

Spring Data JPA

Jackson Databind & jackson-datatype-jsr310

Hibernate

Swagger/OpenAPI (springdoc-openapi-ui)

JWT for authentication

Author
Muhammad Haris Jaffer
Email: harisjaffer@hotmail.com

## API Endpoints

| Method | URL | Description |
|--------|-----|-------------|
| POST   | `/api/translations` | Create a new translation (or update if exists) |
| PUT    | `/api/translations/{id}` | Update translation by ID |
| GET    | `/api/translations/{id}` | Fetch translation by ID |
| GET    | `/api/translations/search` | Search translations (locale, tag, key, content) |
| GET    | `/api/translations/export` | Export translations in JSON format (supports streaming) |

**Example: Export translations using `curl`**
```bash
curl -X GET "http://localhost:8086/api/translations/export?locale=en" \
-H "Authorization: Bearer <YOUR_TOKEN>" \
-o translations.json



