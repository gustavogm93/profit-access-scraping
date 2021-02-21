FROM gcr.io/distroless/java:8
WORKDIR /app
COPY target/people-hub-admin.jar .
EXPOSE 8080 6565
CMD ["people-hub-admin.jar"]