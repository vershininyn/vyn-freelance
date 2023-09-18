FROM openjdk:8-jdk-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
COPY target/apiship-0.1.jar apiship-0.1.jar
ENTRYPOINT ["java","-jar","/apiship-0.1.jar"]
