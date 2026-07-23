# Contexte de build = racine du repo GatchaApi (voir docker-compose.yaml), pour pouvoir
# construire et installer la lib partagée gatcha-common-security avant ce service.
FROM maven:3.9.6-amazoncorretto-21 AS common-build
WORKDIR /common
COPY gatcha-common-security/pom.xml .
COPY gatcha-common-security/src ./src
RUN mvn -q install -DskipTests

FROM maven:3.9.6-amazoncorretto-21 AS build
COPY --from=common-build /root/.m2 /root/.m2
RUN mkdir -p /Api_monstres
COPY API_monstres/src /Api_monstres/src
COPY API_monstres/pom.xml /Api_monstres
WORKDIR /Api_monstres
RUN mvn clean package

FROM amazoncorretto:21.0.2-alpine3.19
COPY --from=build /Api_monstres/target/*.jar /app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-jar", "/app.jar"]
