FROM openjdk:8-jdk-alpine

ARG JAR_FILE

RUN mkdir -p /apps
COPY ./target/${JAR_FILE} /apps/app.jar

ENTRYPOINT ["/usr/bin/java -XX:-UseContainerSupport -Xmx256m -Xss512k -XX:MetaspaceSize=100m -jar /apps/app.jar"]