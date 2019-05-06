
FROM openjdk:8-jdk-alpine

VOLUME /tmp

ADD  ./build/libs/*.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","/app.jar"]