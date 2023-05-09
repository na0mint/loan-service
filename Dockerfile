FROM openjdk:19
COPY target/loan-service-0.0.1-SNAPSHOT.jar loan-service.jar
ENTRYPOINT ["java", "-jar", "/loan-service.jar"]