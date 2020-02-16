#FROM maven.csp:1.0.0 AS build-env
#MAINTAINER chenshaoping <18250073990@163.com>
#
#COPY settings.xml /usr/share/maven/conf
#ADD . /tmp/build
#
#RUN cd /tmp/build \
#        && mvn package -Dmaven.test.skip=test \
#        && mv target/*.jar /app.jar

FROM openjdk:8-jdk-alpine
#COPY --from=build-env /app.jar /app.jar
COPY target/*.jar /app.jar

ENV SERVER_PORT=8761 \
    EUREKA_INSTANCE_HOSTNAME=eureka-server

EXPOSE 8761
ENTRYPOINT ["java", "-jar", "/app.jar"]