# calculator-devops

Simple calculator REST API in Spring Boot, with a CI/CD pipeline around it
(Jenkins + Docker + Docker Compose). Mini project for CSE 816 - Software Production Engineering.

## Requirements

- JDK 17
- Maven 3.9+

## Build and run

```
mvn clean package
java -jar target/calculator.jar
```

App runs on port 8081. Health check: http://localhost:8081/actuator/health
