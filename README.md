# calculator-devops

Simple calculator REST API in Spring Boot, with a CI/CD pipeline around it
(Jenkins + Docker + Docker Compose). Mini project for CSE 816 - Software Production Engineering.

## Requirements

- JDK 17
- Maven 3.9+

## Build, test and run

```
mvn clean package     # build the jar (also runs tests)
mvn test              # only run tests, reports go to target/surefire-reports
java -jar target/calculator.jar
```

App runs on port 8081. Health check: http://localhost:8081/actuator/health

## API

All endpoints take two numbers `a` and `b` as query params.

| Operation | Endpoint |
|-----------|----------|
| Add       | `GET /api/v1/add?a=2&b=3` |
| Subtract  | `GET /api/v1/subtract?a=5&b=1` |
| Multiply  | `GET /api/v1/multiply?a=4&b=2.5` |
| Divide    | `GET /api/v1/divide?a=7&b=2` |
| Health    | `GET /actuator/health` |

Example:

```
$ curl "http://localhost:8081/api/v1/add?a=2&b=3"
{"operation":"add","a":2.0,"b":3.0,"result":5.0}
```

Errors (missing param, not a number, divide by zero) return HTTP 400:

```
$ curl "http://localhost:8081/api/v1/divide?a=5&b=0"
{"status":400,"error":"Bad Request","message":"Division by zero is not allowed","path":"/api/v1/divide"}
```
