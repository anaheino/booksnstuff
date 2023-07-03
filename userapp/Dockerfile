FROM openjdk:17-oracle
COPY target/userapp-docker.jar userapp-docker.jar
ENTRYPOINT ["java", "-jar", "userapp-docker.jar"]
EXPOSE 8081