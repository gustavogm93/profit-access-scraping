FROM openjdk:16.0-jdk-oracle
COPY "./target/profit-access-jar-with-dependencies.jar" "app.jar"
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
