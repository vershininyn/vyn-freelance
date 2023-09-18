FROM openjdk:8-jdk-alpine
MAINTAINER Vershinin Yaroslav <vershininyn@yandex.ru>
EXPOSE 8080
ADD target/customer-service.jar customer-service.jar
ENTRYPOINT ["java", "-jar", "/apiship-service.jar"]
