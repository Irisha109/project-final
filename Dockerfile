FROM maven:3.9.6-eclipse-temurin-17
ARG JAR_FILE=target/jira-1.0.jar
COPY ${JAR_FILE} app.jar

COPY /resources ./resources
COPY /config ./cinfig

EXPOSE 8080
ENTRYPOINT ["java",  "-jar",  "/app.jar", "--spring.profiles.active=prod"]