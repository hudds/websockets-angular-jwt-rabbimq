FROM maven:3.9-eclipse-temurin-22 AS build

WORKDIR /app

COPY pom.xml .
COPY ./src/ ./src/

ENTRYPOINT [ "bash" ]
RUN ["mvn", "install"]

FROM eclipse-temurin:22 AS app

WORKDIR /opt/app

COPY --from=build /app/target/backend-api.jar .

ENTRYPOINT [ "java", "-jar",  "backend-api.jar"]