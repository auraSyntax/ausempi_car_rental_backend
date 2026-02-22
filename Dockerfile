# FROM maven:3.8.5-openjdk-17 AS build
# WORKDIR /app
# COPY . .
# RUN mvn clean package -DskipTests

# # Run Stage
# FROM openjdk:17.0.1-jdk-slim
# WORKDIR /app
# COPY --from=build /app/target/ausempi_car_rental-1.0.0-SNAPSHOT.jar /app/ausempi_car_rental-1.0.0-SNAPSHOT.jar
# EXPOSE 9001
# ENTRYPOINT ["java", "-jar", "/app/ausempi_car_rental-1.0.0-SNAPSHOT.jar"]


# Build Stage
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q clean package -DskipTests

# Run Stage
FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

EXPOSE 8000
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
