FROM maven:3.9.6-amazoncorretto-21 as build
RUN mkdir -p /Api_monstres
COPY src /Api_monstres/src
COPY pom.xml /Api_monstres
WORKDIR /Api_monstres
RUN mvn clean package

FROM amazoncorretto:21.0.2-alpine3.19
COPY --from=build /Api_monstres/target/*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Dspring.profiles.active=docker", "-jar", "/app.jar"]
