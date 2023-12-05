FROM gradle:latest AS builder

WORKDIR /app

COPY build.gradle.kts .
COPY settings.gradle.kts .

COPY src src

RUN gradle build --no-daemon

FROM eclipse-temurin:17.0.9_9-jre-jammy

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]