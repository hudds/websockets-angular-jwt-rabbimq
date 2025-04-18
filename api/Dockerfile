FROM maven:3.9.9-eclipse-temurin-24 AS build

WORKDIR /app

COPY pom.xml .
COPY ./src/ ./src/

ENTRYPOINT [ "bash" ]
RUN ["mvn", "install"]

FROM eclipse-temurin:24 AS app

WORKDIR /opt/app

COPY --from=build /app/target/api.jar .

ENTRYPOINT [ "java", "-jar",  "api.jar", ">>", "api.log"]