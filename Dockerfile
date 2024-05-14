FROM openjdk:17-oracle
ARG JAR_FILE=target/*.jar
COPY .target/dev.orbit-1.0.0.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]