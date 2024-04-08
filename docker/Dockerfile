FROM openjdk:17-jdk-slim AS build
#FROM ubuntu/jre:17_edge

COPY ./build/libs/demo-debezium-postgres-0.0.1-SNAPSHOT.jar demo.jar

#ENTRYPOINT ["java", "-jar", "demo.jar"]
ENTRYPOINT ["bash", "echo", "$JAVA_HOME"]