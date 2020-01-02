FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} carauction.jar
ENTRYPOINT ["java","-jar","/carauction.jar"]