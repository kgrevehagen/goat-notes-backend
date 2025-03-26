FROM amazoncorretto:21-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
