
#FROM maven:3.9.6-openjdk-17-slim AS build
FROM maven:3.9.6-eclipse-temurin-21 AS build


# Set working directory
WORKDIR /app

# Copy pom.xml first for better layer caching
COPY pom.xml .

# Download dependencies
RUN mvn dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN mvn clean package -DskipTests

# Runtime stage
#FROM openjdk:17-jre-slim
FROM eclipse-temurin:21-jre

LABEL maintainer="megastorage2112@gmail.com"
LABEL version="1.0.0"
LABEL description="Notification System Service"

# Create app user for better security
RUN addgroup --system SpringAppGroup && adduser --system --group SpringAppUser

# Set working directory
WORKDIR /app

# Copy jar from build stage
COPY --from=build /app/target/*.jar app.jar

# Change ownership
RUN chown -R SpringAppUser:SpringAppGroup /app

# Switch to non-root user
USER SpringAppUser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1


# Run the application
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]
