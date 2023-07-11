#
# Build stage
#
FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

#
# Package stage
#
FROM openjdk:17-jdk-slim
COPY --from=build /target/note-taking-0.0.1-SNAPSHOT.jar note-taking.jar
# ENV PORT=8080
EXPOSE 8084
ENTRYPOINT ["java","-jar","note-taking.jar"]