FROM maven:3.9.6-amazoncorretto-21 AS build
RUN mkdir -p /api_monstres
COPY src /api_monstres/src
COPY pom.xml /api_monstres
WORKDIR /api_monstres
RUN mvn clean package

FROM amazoncorretto:21.0.2-alpine3.19
COPY --from=build /api_monstres/target/*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
