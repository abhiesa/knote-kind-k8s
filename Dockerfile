FROM adoptopenjdk/openjdk11:alpine-slim
COPY target/*.jar /opt/app.jar
EXPOSE 8080
CMD ["java", "-jar", "/opt/app.jar"]
