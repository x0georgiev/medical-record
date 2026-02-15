# Stage 1: Build the application
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copy gradle configuration files first to cache dependencies
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Copy source code
COPY src ./src

# Build the application (skipping tests to speed up build)
RUN gradle build -x test --no-daemon

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/build/libs/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
