# Use OpenJDK image
#FROM openjdk:17-jdk-slim

# Use OpenJDK image - Alpine is smaller
FROM eclipse-temurin:17-jre-alpine

# Set working directory
WORKDIR /app

# Add a non-root user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy the jar file
COPY --chown=appuser:appgroup target/notification-system.jar app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]