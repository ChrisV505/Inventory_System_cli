# Inventory System CLI

A Spring Boot command-line inventory management system leveraging [Spring Shell](https://spring.io/projects/spring-shell), PostgreSQL, and Lombok annotations.

## Features

- **Product Management**: Add, update, view, and manage inventory products.
- **Category Association**: Organize products by categories.
- **Persistence**: Data is stored in a PostgreSQL database.
- **Command-Line Interface**: Interact with the system using a shell-based UI.
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

2. **Configure the Database**

   Set environment variables or update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=${DB_URL}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}
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

## License

This project currently does not specify a license.

---

For more details, see the [repository on GitHub](https://github.com/ChrisV505/Inventory_System_cli).