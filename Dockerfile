FROM openjdk:17-slim-buster as build
LABEL authors="sejapoe"

WORKDIR /app

COPY .mvn .mvn
COPY mvnw .
COPY pom.xml .
COPY src src

RUN ./mvnw -B clean package

FROM openjdk:17-slim-buster
WORKDIR /app

COPY --from=build /app/target/dz1-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]