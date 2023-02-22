FROM openjdk:17-jdk-slim-buster
COPY target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "app.jar"]