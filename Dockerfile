FROM openjdk:19
EXPOSE 8080
ADD /target/loan-service-0.0.1-SNAPSHOT.jar loan-service.jar
ENTRYPOINT ["java", "-jar", "/loan-service.jar"]