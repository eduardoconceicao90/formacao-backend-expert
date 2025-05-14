FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "/application.jar"]

#docker run --name helpdesk-service-discovery -e CONFIG_SERVER_URI=http://localhost:8888 -p 8761:8761 --network helpdesk_network -d eduardoconceicao90/helpdesk-service-discovery