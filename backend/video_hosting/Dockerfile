FROM maven:3.8.4-openjdk-17 AS build

WORKDIR /build

COPY pom.xml .
COPY src ./src

RUN mvn clean package -T 1C

FROM openjdk:17-jdk-slim

WORKDIR /app

COPY src/main/resources/data/sample-5s.mp4 /app/resources/data/sample-5s.mp4
COPY --from=build /build/target/video_hosting-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
