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