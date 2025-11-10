FROM openjdk:17-ea-33-buster
WORKDIR /app
COPY build/libs/gateway.jar app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]