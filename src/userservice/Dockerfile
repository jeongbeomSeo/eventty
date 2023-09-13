FROM openjdk:17-jdk-slim-buster
WORKDIR /app/user
COPY build/libs/*.jar userservice.jar
ENTRYPOINT ["java", "-jar", "userservice.jar"]