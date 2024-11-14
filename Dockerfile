# --- Build stage for Spring Boot application ---
FROM gradle:7-jdk17-alpine AS build

# Set the working directory
WORKDIR /app

# Copy the Gradle build files
COPY build.gradle.kts settings.gradle.kts ./

# Fetch dependencies
RUN gradle dependencies --no-daemon

# Copy the application source code
COPY . .

# Build the application without running tests
RUN gradle build -x test --no-daemon

# --- Runtime stage ---
FROM openjdk:17-jdk-slim

# Install PostgreSQL
RUN apt-get update && \
    apt-get install -y postgresql postgresql-contrib && \
    rm -rf /var/lib/apt/lists/*

# Set the working directory for the application
WORKDIR /app

# Copy the built application jar from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Copy the init.sql script to set up the database
COPY init.sql /docker-entrypoint-initdb.d/

# Copy the custom entrypoint script
COPY docker-entrypoint.sh /usr/local/bin/
RUN chmod +x /usr/local/bin/docker-entrypoint.sh

# Expose the application port and PostgreSQL port
EXPOSE 8080 5432

# Use the entrypoint script
ENTRYPOINT ["docker-entrypoint.sh"]
