# build stage
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# download dependencies first so this layer is cached when only code changes
COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
# tests already run in the jenkins Test stage
RUN mvn -B clean package -DskipTests

# run stage
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S app && adduser -S app -G app

COPY --from=build /app/target/calculator.jar app.jar

USER app
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]
