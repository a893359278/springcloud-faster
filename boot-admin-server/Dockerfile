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
ENV ADMIN_USERNAME=admin \
    ADMIN_PASSWORD=admin \
    EUREKA_SERVICE_URL=http://eureka-server:8761/eureka
EXPOSE 9110
ENTRYPOINT ["java", "-jar", "/app.jar"]