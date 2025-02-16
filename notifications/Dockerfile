FROM openjdk:22-jdk-slim

WORKDIR /app

# Skopiowanie plików źródłowych
COPY . .

# Budowanie aplikacji bez testów
RUN apt-get update && apt-get install -y maven && \
    mvn clean install -DskipTests && \
    rm -rf /var/lib/apt/lists/*

# Pobranie zbudowanego pliku JAR
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Uruchomienie aplikacji
ENTRYPOINT ["java", "-jar", "/app.jar"]