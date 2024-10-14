FROM openjdk:17-jdk-slim
ARG JAR_FILE=build/libs/animal-tracking-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]