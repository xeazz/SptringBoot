FROM openjdk:17-jdk-slim-buster
EXPOSE 8081
ADD target/SpringBoot-0.0.1-SNAPSHOT.jar myapp.jar
CMD ["java", "-jar", "/myapp.jar"]