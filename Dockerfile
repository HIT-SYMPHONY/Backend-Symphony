# Use official OpenJDK base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory in container
WORKDIR /app

# Copy built JAR file into container (adjust JAR name if needed)
COPY target/BackendSymphony-0.0.1-SNAPSHOT.jar app.jar

# Expose port used by Spring Boot
EXPOSE 8080

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
