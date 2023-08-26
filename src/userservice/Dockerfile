FROM openjdk:17-jdk-slim-buster
COPY build/libs/*.jar userservice.jar
ENTRYPOINT ["java", "-jar", "userservice.jar"]