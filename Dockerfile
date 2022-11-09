FROM eclipse-temurin:17-alpine
COPY target/achat-1.0.jar .
ENTRYPOINT ["java","-jar","/achat-1.0.jar"]