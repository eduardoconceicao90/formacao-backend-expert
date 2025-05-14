FROM openjdk:21-jdk
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} application.jar
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/application.jar"]

#docker run --name helpdesk-config-server -e CONFIG_SERVER_URI=https://github.com/eduardoconceicao90/formacao-backend-expert-configs -e CONFIG_SERVER_USERNAME=eduardoconceicao90 -e CONFIG_SERVER_PASSWORD=********************** -p 8888:8888 --network helpdesk_network -d eduardoconceicao90/helpdesk-config-server