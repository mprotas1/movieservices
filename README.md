# Cinema Microservices distributed application

The application is part of a research paper on comparing microservices architecture with monolithic architecture. It deals with the differences between these architectural patterns.

## Table of contents
* [Description](#description)
* [Microservices](#microservices)
* [Technology stack](#technology-stack)
* [Requirments](#requirments)
* [Installation guide](#installation)

## Description
The Cinema Microservices Application is a model for an enterprise application that oversees all cinema-based operations. It consists of user management, theatre maintenance, movie repertoire updates, and other related tasks. Dependencies are split up between list of microservices which has their own responsibilites. The application is built using REST API architectural styles in concjuction with the microservices architectural pattern.

## Microservices
* users service [Java 22 + PostgreSQL stack]
* cinemas service [Java 22 + MongoDB stack]
* cloud gateway [Java Spring Cloud Gateway implementation]
* recommendations service

## Technology stack
* Spring Boot
* JUnit5 and Mockito
* Testcontainers
* Docker
* RabbitMQ
* Eureka Server
* PostgreSQL
* MongoDB
* Redis

## Requirments
The application (every service) needs for correct boot at least Java with version 21 and a running Docker Deamon for running all dependent containers.

## Installation
To install the application you need to have an active and running Docker Deamon.
