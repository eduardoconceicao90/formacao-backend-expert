FROM gradle:8.5-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar application.jar
ENTRYPOINT ["java", "-jar", "/app/application.jar"]