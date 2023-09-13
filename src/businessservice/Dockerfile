FROM openjdk:17-jdk-slim-buster
COPY build/libs/*.jar event.jar
ENTRYPOINT ["java", "-jar", "event.jar"]