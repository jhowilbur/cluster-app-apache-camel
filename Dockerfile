FROM openjdk:11-jdk-slim

MAINTAINER Wilbur

ARG  APP_HOST="localhost"
ARG  ENV_APP=""

ENV  APP_HOST $APP_HOST
ENV  ENV_APP $ENV_APP

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--spring.profiles.active=${ENV_APP}"]