FROM openjdk:8-jdk-alpine

ARG JAR_FILE
EXPOSE 8080
RUN mkdir -p /apps
COPY ./target/${JAR_FILE} /apps/app.jar

ENTRYPOINT ["java","-jar","/apps/app.jar"]