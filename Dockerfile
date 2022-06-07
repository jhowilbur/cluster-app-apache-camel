FROM openjdk:11-jdk-slim

ARG  DB_HOST="localhost"
ARG  DB_USERNAME="postgres"
ARG  DB_PASSWORD="admin"
ARG  ENV_APP=""

ENV  DB_HOST $DB_HOST
ENV  DB_USERNAME $DB_USERNAME
ENV  DB_PASSWORD $DB_PASSWORD
ENV  ENV_APP $ENV_APP

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=${ENV_APP}"]