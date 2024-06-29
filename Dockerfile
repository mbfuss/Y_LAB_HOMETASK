FROM openjdk:11-jre-slim
COPY target/coworking-service-1.0-SNAPSHOT.jar /usr/local/lib/coworking-service-1.0-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/usr/local/lib/coworking-service-1.0-SNAPSHOT.jar"]
