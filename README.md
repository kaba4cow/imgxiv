# imgxiv (WIP)

RESTful tag-based image archive.

## Stack

- Java 17
- Maven
- PostgreSQL
- Spring Boot 3.5
- Spring Web
- Spring Security
- Spring Data JPA
- Spring Validation
- Springdoc OpenAPI (Swagger UI)

## How to run

Clone repository:

```bash
git clone https://github.com/kaba4cow/imgxiv.git
cd imgxiv
```

Run using maven:

```bash
# On Linux/MacOS
./mvnw spring-boot:run
```

```bash
# On Windows (cmd)
mvnw.cmd spring-boot:run
```

### Requirements

- Java 17 or higher
- Maven

## API Documentation

This project includes an automatically generated Swagger UI for testing the API.

- Swagger UI: `http://<host>:<port>/docs`
- OpenAPI JSON: `http://<host>:<port>/api/docs`

## License

This project is licensed under the **MIT License** - see the [LICENSE](./LICENSE) file for details.