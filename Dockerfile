FROM openjdk:8-jdk-alpine
EXPOSE 8081
ADD build/libs/spring-boot-conditional-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]