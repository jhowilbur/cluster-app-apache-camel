FROM openjdk:11-jdk-slim

MAINTAINER Wilbur

ARG  DB_HOST="localhost"
ARG  ENV_APP=""

ENV  DB_HOST $DB_HOST
ENV  ENV_APP $ENV_APP

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=${ENV_APP}"]