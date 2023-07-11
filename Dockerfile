FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-jdk-slim

EXPOSE 8084

COPY --from=build /build/libs/note-taking-be.jar note-taking-be.jar

ENTRYPOINT ["java", "-jar", "note-taking-be.jar"]