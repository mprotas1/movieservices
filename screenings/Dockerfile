FROM openjdk:22-jdk-slim
LABEL authors="mprotas"

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]