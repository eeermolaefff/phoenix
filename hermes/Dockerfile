#
# Build stage
#
FROM maven:3.9.9-eclipse-temurin-21 AS build

COPY src /home/app/src
COPY pom.xml /home/app
COPY --from=common /common/common-alpha.jar /home/app/lib/common-alpha.jar

RUN mvn install:install-file \
       -Dfile=/home/app/lib/common-alpha.jar \
       -DgroupId=com.phoenix \
       -DartifactId=common \
       -Dversion=alpha \
       -Dpackaging=jar \
       -DgeneratePom=true

RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM eclipse-temurin:21

COPY --from=build /home/app/target/hermes-alpha.jar /home/app/hermes-alpha.jar

WORKDIR /home/app
CMD ["java", "-cp", "hermes-alpha.jar", "com.phoenix.hermes.Application"]