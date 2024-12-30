#
# Build stage
#
FROM maven:3.9.4-eclipse-temurin-17 AS build
COPY src /home/app/src
COPY pom.xml /home/app

RUN mvn install:install-file \
       -DgroupId=com.pantheon \
       -Dversion=alpha \
       -Dpackaging=jar \
       -DgeneratePom=true

RUN mvn -f /home/app/pom.xml clean package


#
# Package stage
#
FROM openjdk:22-slim
COPY --from=build /home/app/target/hermes-alpha-jar-with-dependencies.jar /home/app/hermes-alpha-jar-with-dependencies.jar

WORKDIR /home/app
CMD ["java", "-cp", "hermes-alpha-jar-with-dependencies.jar", "com.pantheon.hermes.Application"]