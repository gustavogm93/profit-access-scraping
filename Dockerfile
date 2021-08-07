FROM openjdk:16.0-jdk-oracle
COPY "./target/profit-access.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]



