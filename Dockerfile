FROM openjdk:21-jdk as builder

WORKDIR /code
COPY ./ ./
RUN gradle build --no-daemon

FROM eclipse-temurin:21

COPY --from=builder /code/target/*.jar /app.jar
## Need to either configure temporal server using env variables as this will be failing on startup.
CMD ["java", "-jar", "/app.jar"]