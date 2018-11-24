FROM openjdk:11-jdk
COPY build/libs/*.jar /app/application.jar
CMD ["java", "-jar", "app/application.jar"]