FROM maven:3.5-jdk-8-alpine as maven

COPY ./pom.xml ./pom.xml
COPY ./src ./src

RUN mvn clean install

FROM openjdk:8-jdk-alpine

WORKDIR /app

COPY --from=maven target/*.jar ./app.jar
CMD ["java", "-jar", "./app.jar"]