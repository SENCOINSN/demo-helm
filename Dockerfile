# Stage 1: Build the Spring Boot application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
# Package the application
RUN mvn clean package -DskipTests
# Stage 2: Create the final image
FROM eclipse-temurin:17-jdk-jammy
# Set the working directory
WORKDIR /app
# Copy the built JAR file from the build stage
COPY --from=build /app/target/demo-helm-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the application will run on
EXPOSE 8083
# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]