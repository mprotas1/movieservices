# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk-slim

MAINTAINER "Michał Protas"
LABEL authors="Michał Protas"
LABEL description="Dockerfile for the cinemas microservice application"

# Set the working directory in the container
WORKDIR /app

# Copy the generated .jar file to the container
COPY target/cinemas-1.0-SNAPSHOT.jar app.jar

# Run the .jar file
ENTRYPOINT ["java", "-jar", "/app.jar"]