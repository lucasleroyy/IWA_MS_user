#!/bin/bash

# Start PostgreSQL service
service postgresql start

# Wait for PostgreSQL to be ready
until pg_isready; do
  echo "Waiting for PostgreSQL to start..."
  sleep 2
done

# Start the Spring Boot application
exec java -jar /app/app.jar
