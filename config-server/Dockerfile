FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/application.jar"]