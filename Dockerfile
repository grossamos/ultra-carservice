FROM openjdk:11-slim

COPY ./target/car-service-0.0.1-SNAPSHOT.jar /ultra-service/service.jar

ENTRYPOINT java -jar /ultra-service/service.jar
