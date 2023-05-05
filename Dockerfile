FROM openjdk:19
#EXPOSE 8080
COPY target/loan-service-0.0.1-SNAPSHOT.jar loan-service.jar
CMD ["java", "-jar", "loan-service.jar"]