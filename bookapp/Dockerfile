FROM openjdk:17-oracle
COPY target/bookapp-docker.jar bookapp-docker.jar
ENTRYPOINT ["java", "-jar", "bookapp-docker.jar"]
EXPOSE 8080