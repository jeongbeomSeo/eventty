FROM eclipse-temurin:17
WORKDIR /app/user
COPY build/libs/*.jar userservice.jar
ENTRYPOINT ["java", "-jar", "userservice.jar"]