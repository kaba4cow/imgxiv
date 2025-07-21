# [imgxiv](https://github.com/kaba4cow/imgxiv) (WIP)

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

## Overview

**imgxiv** is a RESTful image archive focused on tag-based content discovery.

## Features

- JWT-based authentication
- Tag-based post querying
- Commenting and voting on posts
- In-memory caching
- Swagger UI for testing the API
- Role-based access control

## Getting Started

### Requirements

- Java 17 or higher
- Maven

### Running the Application

Clone and run the project using Maven:

```bash
git clone https://github.com/kaba4cow/imgxiv.git
cd imgxiv

# Linux/MacOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

## API Documentation

This project includes an automatically generated Swagger UI for testing the API.

- [Swagger UI](http://localhost:8080/docs) at `/docs`
- [OpenAPI JSON](http://localhost:8080/api/docs) at `/api/docs`

## Stack

### Platform

- Java 17
- Maven
- Spring Boot 3.5

### Database

- [Spring Data JPA](https://github.com/spring-projects/spring-data-jpa)
- [Hibernate ORM](https://github.com/hibernate/hibernate-orm)
- [PostgreSQL](https://github.com/postgres/postgres)
- [H2 Database](https://github.com/h2database/h2database) for development and testing

### Web

- Spring Web for building REST controllers
- [Hibernate Validation](https://github.com/hibernate/hibernate-validator) for validating user input
- [Springdoc OpenAPI](https://github.com/springdoc/springdoc-openapi) with [Swagger UI](https://github.com/swagger-api/swagger-ui)

### Security

- [Spring Security](https://github.com/spring-projects/spring-security)
- [JJWT](https://github.com/jwtk/jjwt) for JSON Web Token authentication

### Caching

- Spring Cache
- [Caffeine](https://github.com/ben-manes/caffeine) for in-memory caching

### Testing

- Spring Boot Test
- MockMvc for REST API testing

### Misc

- [Lombok](https://github.com/projectlombok/lombok) for boilerplate reduction

## License

This project is licensed under the **MIT License** - see the [LICENSE](./LICENSE) file for details.