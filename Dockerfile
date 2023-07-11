FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.jar note-taking-be.jar
ENTRYPOINT ["java","-jar","/note-taking-be.jar"]
EXPOSE 8084