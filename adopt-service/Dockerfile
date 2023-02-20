FROM amazoncorretto:17
MAINTAINER 0109199416081991
ARG JAR_FILE=target/adopt-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/adopt-service-0.0.1-SNAPSHOT.jar"]