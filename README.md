# Car Service
## Monolith Spring Boot Application
### Description
Car Service is a web application for customers and employees of a car service.
Main possibilities are:
- Creation, editing and placement of an `Order`.
- Automatic creation of an `Assignment` and selection of an appropriate `Employee` depending on his `Specialization` and available `Periods` of time.
- Update of the `AssignmentStatus` and `OrderStatus` as work progresses.
- `Employee` management.
### Technologies
- **PostgreSQL** as a database.
- **Liquibase** for managing database changes.
- **MyBatis** is used for communication with database and mapping of models.
- **JDBC API** was used previously instead of MyBatis. Those `Repositories` are still in code, but disabled.
- **MapStruct** helps to map DTOs to entities and vice versa.
- **OpenAPI** generates documentation for `Controllers` and their endpoints.
- **JUnit 5** and **Mockito** are used for testing of the service layer.
- **Jacoco** generates report on testing.
- **Spring Security** and **JWT** tokens provide authentication and authorization. 
- **Docker** is used to create an image of a Spring Boot application.
- **Docker Compose** runs database container and main application container.
- **GitHub Actions** manage workflow, which runs tests and pushes Docker image to DockerHub.
- **Kubernetes** automatically deploys and manages containerized application and database.
### How to run
- With Docker Compose
1. Clone project: `git clone https://github.com/gznznzjsn/car-service`
2. Go to project folder and package it: `mvn clean install`
3. Create `.env` file with `CAR-SERVICE_SECRETS_ACCESS-KEY`, `CAR-SERVICE_SECRETS_REFRESH-KEY`, `POSTGRES_USER`, `POSTGRES_PASSWORD`
4. Start application: `docker compose up`
5. Open **Swagger**: http://localhost:8080/api/v1/swagger-ui/index.html
6. Feel free to use!
- With Kubernetes:
...


[//]: # (- [API Gateway]&#40;https://github.com/gznznzjsn/api-gateway&#41; - single entrypoint for all clients, routes requests to appropriate microservices.)

[//]: # (- [Service discovery and service registry]&#40;https://github.com/gznznzjsn/discovery-server&#41; - registers and holds addresses of microservice instances, simplifies discovery of services and communication between them.)

