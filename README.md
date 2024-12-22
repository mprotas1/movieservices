# Cinema Microservices distributed application

The application is part of a research paper on comparing microservices architecture with monolithic architecture. It deals with the differences between these architectural patterns.

## Table of contents
* [Description](#description)
* [Microservices](#microservices)
* [Technology stack](#technology-stack)
* [Requirments](#requirments)
* [Installation guide](#installation)

## Description
The Cinema Microservices Application is a scalable and modular enterprise system designed to manage all aspects of cinema operations. This project demonstrates the power of microservices architecture combined with REST API principles to deliver a robust and efficient solution for modern cinema networks.

The application is tailored to handle critical tasks such as user management, theater maintenance, movie scheduling, ticket reservations, and notifications, while also laying the groundwork for advanced services like personalized recommendations. Each microservice is focused on a specific domain, ensuring high cohesion and loose coupling, which are hallmarks of microservices design.

## Microservices
* users service [Java 22 + Spring Boot] - handling user creation, authentication with JWT Token and user data management (including roles)
* cinemas service [Java 22 + Spring Boot] - management of the cinema network and display functionality for potential customers
* movies service [Python + FastAPI] - storage of movies that are displayed in our theatres
* screenings service [Java 22 + Spring Boot] - management of the occurrences of the film in specific cinema theatre
* reservations service [Java 22 + Spring Boot] - maintaining user reservations
* notifications service - planned [GOLang + gin] - sending proper notifications to users with informations about their reservations, recommendations et cetera
* cloud gateway [Java Spring Cloud Gateway implementation]
* recommendations service planned

## Technology stack
* Spring Boot
* Python
* GOLang
* JUnit5 and Mockito
* Testcontainers
* Docker
* RabbitMQ / Kafka
* Eureka Server
* PostgreSQL
* HashiCorp Vault
* Google Maps API

## Requirments
The application (every Java service) needs for correct boot at least Java with version 21 and a running Docker Deamon for running all dependent containers. For Python services - use Python 3 and FastAPI.

## Installation
To install the application, you must have an active and running Docker daemon.
