# [imgxiv](https://github.com/kaba4cow/imgxiv) (WIP)

![Java](https://img.shields.io/badge/Java-17-blue.svg)
![License](https://img.shields.io/badge/license-MIT-green.svg)

## Overview

**imgxiv** is a RESTful image archive focused on tag-based content discovery.

## Features

- JWT-based authentication
- Tag-based post querying
- In-memory caching
- Swagger UI for testing the API
- Role-based access control

### Planned

- Comments and voting system
- Image upload
- Content moderation

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

- [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
- [Hibernate ORM](https://hibernate.org/orm/)
- [PostgreSQL](https://www.postgresql.org/)
- [H2 Database](https://www.h2database.com/html/main.html) for development and testing

### Web

- [Spring Web](https://docs.spring.io/spring-boot/reference/web/index.html) for building REST controllers
- [Spring Validation](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-validation.html) for validating user input
- [Springdoc OpenAPI](https://springdoc.org/) with [Swagger UI](https://swagger.io/tools/swagger-ui/)

### Security

- [Spring Security](https://spring.io/projects/spring-security)
- [JJWT](https://github.com/jwtk/jjwt) for JSON Web Token authentication

### Caching

- [Spring Cache](https://spring.io/guides/gs/caching)
- [Caffeine](https://github.com/ben-manes/caffeine) for in-memory caching

### Testing

- [Spring Boot Test](https://docs.spring.io/spring-boot/reference/testing/index.html)
- [MockMvc](https://docs.spring.io/spring-framework/reference/6.1/testing/spring-mvc-test-framework.html) for REST API testing

### Misc

- [Lombok](https://projectlombok.org/) for boilerplate reduction

## License

This project is licensed under the **MIT License** - see the [LICENSE](./LICENSE) file for details.