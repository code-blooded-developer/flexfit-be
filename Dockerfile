FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app

COPY target/flexfit-0.0.1-SNAPSHOT.jar flexfit.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","flexfit.jar"]