FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . /app
RUN ls -l gradlew
RUN chmod +x gradlew

RUN ./gradlew clean build -x test
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
