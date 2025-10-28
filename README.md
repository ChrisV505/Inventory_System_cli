# Inventory System CLI

A Spring Boot command-line inventory management system leveraging [Spring Shell](https://spring.io/projects/spring-shell), PostgreSQL, and Lombok annotations.

## Features

- **Product Management**: Add, update, view, and manage inventory products.
- **Category Association**: Organize products by categories.
- **Stock Updation**: Update and monitor stock quantities directly via CLI commands. Stock alerts are triggered when inventory falls below threshold.
- **Addition Commands**: Easily add new products through enhanced shell commands.
- **Email Sending**: Automated notifications for low stock and inventory events are sent via email (simple email and with attachments supported).  
  Uses **GMAIL SMTP** for email transport.
- **AOP (Aspect-Oriented Programming)**: Uses Spring AOP for:
    - Logging and performance monitoring of command executions
    - Sending stock alerts after usage updates
- **Table UI**: Tabular display of products using Spring Shell table UI for improved CLI output readability.
- **Persistence**: Data is stored in a PostgreSQL database.
- **Command-Line Interface**: Interact with the system using a Spring Shell-based UI.
- **Logging**: Activity is logged to `inventory.log`.

## Requirements

- Java 21
- Maven
- PostgreSQL

## Getting Started

1. **Clone the repository**
   ```sh
   git clone https://github.com/ChrisV505/Inventory_System_cli.git
   cd Inventory_System_cli
   ```

2. **Configure the Database & Email**

   Set environment variables or update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
   # Gmail SMTP configuration for email sending
   spring.mail.host=smtp.gmail.com
   spring.mail.port=587
   spring.mail.username=${EMAIL_USERNAME}
   spring.mail.password=${EMAIL_PASSWORD}
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true
   ```

3. **Build and Run**

   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

4. **Using the CLI**

   With the application running, you can interactively manage your inventory via the Spring Shell interface.

## Testing (H2 embedded)

This project supports running tests using an embedded H2 database so you don't need a running PostgreSQL instance for test execution. Below are notes and examples to run and configure the test environment.

- How tests are organized:
    - Unit tests typically use slice tests (e.g., @DataJpaTest) or Mockito for service-level tests.
    - Integration tests use @SpringBootTest and are configured to run against an in-memory H2 database to isolate them from your production PostgreSQL database.

- Typical commands to run tests:
    - Run all tests:
      ```sh
      mvn test
      ```
    - Run tests with the "test" profile (if you prefer a test profile):
      ```sh
      mvn -Dspring.profiles.active=test test
      ```

- Example test configuration (create `src/test/resources/application-test.properties` or `src/test/resources/application.properties` for tests):
  ```properties
  # Use H2 in-memory database for tests
  spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false
  spring.datasource.driverClassName=org.h2.Driver
  spring.datasource.username=sa
  spring.datasource.password=
  spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
  spring.jpa.hibernate.ddl-auto=create-drop
  spring.h2.console.enabled=true
  ```

- Recommended annotations for tests that should use H2:
    - For repository/JPA slice tests:
      ```java
      @DataJpaTest
      @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
      public class ProductRepositoryTest { ... }
      ```
    - For full integration tests:
      ```java
      @SpringBootTest
      @ActiveProfiles("test") // if you use application-test.properties
      public class InventoryIntegrationTest { ... }
      ```

- Why use H2 for tests:
    - Fast in-memory execution.
    - Easier CI integration (no external DB dependency).
    - Tests are isolated and can use create-drop schema handling to start clean each run.

- Notes:
    - The application still uses PostgreSQL in non-test environments (see main `application.properties`). H2 is only recommended for tests and local verification.
    - If you have tests that rely on PostgreSQL-specific behavior (functions, extensions, or SQL), consider marking them as integration tests and running them against a PostgreSQL instance or a testcontainer setup.

## Technologies Used

- **Spring Boot**: Application framework.
- **Spring Shell**: Command-line interface.
- **Spring Data JPA**: ORM for database interaction.
- **PostgreSQL**: Relational database.
- **Lombok**: Reduces boilerplate code.
- **Spring AOP**: Aspect-oriented programming for logging, monitoring, and stock alert triggers.
- **Spring Boot Mail**: For sending email notifications (via Gmail SMTP).
- **Spring Shell Table**: For tabular CLI output.

## Example Model

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Integer stock;
    private BigDecimal price;
    private BigDecimal total;
    private String code;
    private List<Category> category;
}
```

## Stock Alerts & Email Integration

- Stock alert emails are triggered automatically using AOP when a product's stock falls below the threshold (default: 10).
- Both simple and attachment emails are supported for notifications.
- Email delivery is handled through Gmail SMTP.

---

For more details, see the [repository on GitHub](https://github.com/ChrisV505/Inventory_System_cli).