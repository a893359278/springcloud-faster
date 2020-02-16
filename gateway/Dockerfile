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
ENV REDIS_HOST=redis \
    REDIS_PORT=6379 \
    EUREKA_SERVICE_URL=http://eureka-server:8761/eureka
EXPOSE 11110
ENTRYPOINT ["java", "-jar", "/app.jar"]