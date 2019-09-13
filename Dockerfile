FROM openjdk:12-alpine

MAINTAINER dennis ngeno (dennisngeno7@gmail.com)

RUN apk upgrade

RUN apk add maven

EXPOSE 8500:8500

COPY pom.xml /usr/local/service/pom.xml

COPY src /usr/local/service/src

WORKDIR /usr/local/service

RUN mvn clean

RUN  mvn clean compile assembly:single

ENTRYPOINT ["java", "-jar", "target/leaning-odbc-1.0-SNAPSHOT-jar-with-dependencies.jar"]